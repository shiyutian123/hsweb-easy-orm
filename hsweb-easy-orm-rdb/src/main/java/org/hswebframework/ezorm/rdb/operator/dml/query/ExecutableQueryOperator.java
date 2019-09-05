package org.hswebframework.ezorm.rdb.operator.dml.query;

import lombok.AllArgsConstructor;
import org.hswebframework.ezorm.rdb.executor.SqlRequest;
import org.hswebframework.ezorm.rdb.executor.wrapper.ResultWrapper;
import org.hswebframework.ezorm.rdb.metadata.RDBDatabaseMetadata;
import org.hswebframework.ezorm.rdb.metadata.TableOrViewMetadata;
import org.hswebframework.ezorm.rdb.operator.builder.fragments.query.QuerySqlBuilder;

@AllArgsConstructor
public class ExecutableQueryOperator extends BuildParameterQueryOperator {

    private RDBDatabaseMetadata metadata;

    @Override
    public SqlRequest getSql() {
        return metadata.getTableOrView(this.getParameter().getFrom())
                .flatMap(tableOrView -> tableOrView.<QuerySqlBuilder>findFeature(QuerySqlBuilder.id))
                .map(builder -> builder.build(this.getParameter()))
                .orElseThrow(() -> new UnsupportedOperationException("unsupported query operator"));
    }

    @Override
    public <E, R> QueryResultOperator<E, R> fetch(ResultWrapper<E, R> wrapper) {
        String from = this.getParameter().getFrom();
        TableOrViewMetadata tableOrViewMetadata = metadata
                .getTableOrView(this.getParameter().getFrom())
                .orElseThrow(() -> new UnsupportedOperationException("table or view [" + from + "] doesn't exist "));

        return new QueryResultOperator<>(getSql(), tableOrViewMetadata, wrapper);
    }
}

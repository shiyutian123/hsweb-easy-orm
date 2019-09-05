package org.hswebframework.ezorm.rdb.executor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DefaultBatchSqlRequest extends PrepareSqlRequest implements BatchSqlRequest {

    public static DefaultBatchSqlRequest of(String sql, Object... parameter) {
        DefaultBatchSqlRequest sqlRequest = new DefaultBatchSqlRequest();
        sqlRequest.setSql(sql);
        sqlRequest.setParameters(parameter);
        return sqlRequest;
    }

    @Getter
    @Setter
    private List<SqlRequest> batch = new ArrayList<>();


    public DefaultBatchSqlRequest addBatch(SqlRequest sqlRequest) {
        batch.add(sqlRequest);
        return this;
    }

}

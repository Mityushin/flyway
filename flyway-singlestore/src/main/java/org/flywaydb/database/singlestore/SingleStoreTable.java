/*
 * Copyright (C) Red Gate Software Ltd 2010-2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.database.singlestore;

import lombok.CustomLog;
import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

import java.sql.SQLException;

@CustomLog
public class SingleStoreTable extends Table<SingleStoreDatabase, SingleStoreSchema> {

    SingleStoreTable(JdbcTemplate jdbcTemplate, SingleStoreDatabase database, SingleStoreSchema schema, String name) {
        super(jdbcTemplate, database, schema, name);
    }

    @Override
    protected void doDrop() throws SQLException {
        jdbcTemplate.execute("DROP TABLE " + database.quote(schema.getName(), name));
    }

    @Override
    protected boolean doExists() throws SQLException {
        return exists(schema, null, name);
    }

    @Override
    protected void doLock() throws SQLException {
        if (jdbcTemplate.queryForString("select storage_type from information_schema.tables where table_schema=? and table_name=?", schema.getName(), name).equals("COLUMNSTORE")) {
            LOG.warn("Taking lock on columnstore table is not supported by SingleStoreDB");
        } else {
            jdbcTemplate.execute("SELECT * FROM " + this + " FOR UPDATE");
        }
    }
}
CREATE KEYSPACE sort WITH replication = {'class': 'NetworkTopologyStrategy', 'dc1': '1'}  AND durable_writes = true;

CREATE TABLE sort.sort_table (
    owner text,
    id text,
    name text,
    shortname text,
    PRIMARY KEY (owner, id)
);

COPY sort.sort_table FROM 'sort_data.csv';

select * from sort.sort_table where solr_query = '{"q": "*:*", "sort": "shortname_ci asc"}';
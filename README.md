# dse-fit

Set of tools for customing Datastax Enterprise Stack (Cassandra and Solr)

Usage:

1. First, must have to downlaod Datastax Enterprise 

https://portal.datastax.com/downloads.php?dsedownload=tar/enterprise/dse.tar.gz

2. Then add following packages into local Maven repository

mvn install:install-file -Dfile=[path to datastax]/dse-6.7.2/resources/solr/lib/solr-uber-with-auth_2.1-6.0.1.2.2381.jar -DgroupId=org.apache.solr -DartifactId=solr-core -Dversion=6.0.1.2.2381 -Dpackaging=jar
mvn install:install-file -Dfile=[path to datastax]/dse-6.7.2/lib/dse-search-6.7.2.jar -DgroupId=com.datastax  -DartifactId=dse-search -Dversion=6.7.2 -Dpackaging=jar

3. Run maven build
4. Copy jar file into [datastax server instalation directory]/dse/resources/solr/lib

5. Put this line into your solrconfig.file:
<fieldInputTransformer name="dse" class="com.artwork.mori.dse.search.LowerCaseFieldInputTransformer"></fieldInputTransformer>

# FIT Copy Transformers

- Insert lowercase value into [name]_ci document field

package com.artwork.mori.dse.search;

import com.datastax.bdp.search.solr.FieldInputTransformer;
import org.apache.lucene.document.Document;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LowerCaseFieldInputTransformer extends FieldInputTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LowerCaseFieldInputTransformer.class);

    @Override
    public boolean evaluate(String field) {
        return field.equals(Fields.SHORT_NAME.name) || field.equals(Fields.NAME.name);
    }

    @Override
    public void addFieldToDocument(SolrCore core,
                                   IndexSchema schema,
                                   String key,
                                   Document doc,
                                   SchemaField fieldInfo,
                                   String fieldValue,
                                   DocumentHelper helper) throws IOException {
        try {
            LOGGER.info("Custom FIT transforming - key: {}, value: {}", key, fieldValue);
            if (fieldInfo.getName().equals(Fields.SHORT_NAME.name)) {
                writeField(core, key, doc, fieldValue, helper, Fields.SHORT_NAME);
            } else if (fieldInfo.getName().equals(Fields.NAME.name)) {
                writeField(core, key, doc, fieldValue, helper, Fields.NAME);
            }
        } catch (Exception ex) {
            LOGGER.error("Custom FIT transforming failed - key: {}, value: {}. Reason: {}", key, fieldValue, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void writeField(SolrCore core, String key, Document doc, String fieldValue, DocumentHelper helper, Fields field)
            throws IOException {
        LOGGER.info("Custom FIT transforming for field: {}", field.name);
        String lowerCaseFieldValue = fieldValue.toLowerCase();
        SchemaField lowerCaseField = core.getLatestSchema().getFieldOrNull(field.name + "_ci");
        helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, lowerCaseField, lowerCaseFieldValue);
        LOGGER.info("Custom FIT transforming for field: {} - lowercased: {}", lowerCaseField);
    }

    enum Fields {

        SHORT_NAME("shortname"),
        NAME("name");

        String name;

        Fields(String name) {
            this.name = name;
        }
    }
}

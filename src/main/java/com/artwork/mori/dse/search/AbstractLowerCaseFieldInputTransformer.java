package com.artwork.mori.dse.search;

import com.datastax.bdp.search.solr.FieldInputTransformer;
import org.apache.lucene.document.Document;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractLowerCaseFieldInputTransformer extends FieldInputTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLowerCaseFieldInputTransformer.class);
    private final Set<String> fields;

    public AbstractLowerCaseFieldInputTransformer(Set<String> fields) {
        this.fields = fields;
    }

    @Override
    public boolean evaluate(String field) {
        return fields.contains(field);
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
            for (String field : this.fields) {
                if (fieldInfo.getName().equals(field)) {
                    LOGGER.info("Custom FIT transforming - key: {}, value: {}", key, fieldValue);
                    this.writeField(core, key, doc, fieldValue, helper, field);
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Custom FIT transforming failed - key: {}, value: {}. Reason: {}", key, fieldValue, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void writeField(final SolrCore core,
                            final String key,
                            final Document doc,
                            final String fieldValue,
                            final DocumentHelper helper,
                            final String field)
            throws IOException {

        LOGGER.info("Custom FIT transforming for field: {}", field);

        final String lowerCaseFieldValue = fieldValue.toLowerCase();
        final SchemaField lowerCaseField = core.getLatestSchema().getField(field + "_ci");
        final SchemaField normalField = core.getLatestSchema().getField(field);

        helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, lowerCaseField, lowerCaseFieldValue);
        helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, normalField, fieldValue);

        LOGGER.info("Custom FIT transforming for field: {} - lowercased: {}", lowerCaseField);
    }
}

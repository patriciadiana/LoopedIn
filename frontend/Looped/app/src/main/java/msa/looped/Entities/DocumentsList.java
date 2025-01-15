package msa.looped.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentsList {
    @SerializedName("documentsList")
    private List<Document> documentsList;

    public List<Document> getDocumentsList() {
        return documentsList;
    }

    public Document get(int position)
    {
        return documentsList.get(position);
    }

    @Override
    public String toString() {
        return "DocumentsList{" +
                "documentsList=" + documentsList +
                '}';
    }
}


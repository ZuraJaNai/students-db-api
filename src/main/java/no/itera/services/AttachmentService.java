package no.itera.services;

import no.itera.model.Attachment;
import java.util.List;

public interface AttachmentService {

    boolean addFile(int personId, byte[] buffer, String originalFilename, String contentType);

    void deleteFile(int attachmentId);

    List<Attachment> getAttachments(int personId);

    Attachment getFile(int personId, int attachmentId);

    boolean isPersonHasFile(int personId, String originalFilename);

    boolean isPersonHasFile(int personId, int attachmentId);
}

package no.itera.services;

import no.itera.model.Attachment;
import java.util.List;

public interface AttachmentService {

    /**
     * Method to add file to instance of Person class
     *
     * @param personId  id of person to whom to add file
     * @param buffer  byte array containing file data
     * @param originalFilename  name of the file
     * @param contentType  content type of the file
     * @return Boolean true or false
     */
    boolean addFile(int personId, byte[] buffer, String originalFilename, String contentType);

    /**
     * Method to delete attachment from the database
     *
     * @param attachmentId  id of attachment to be deleted
     */
    void deleteFile(int attachmentId);

    /**
     * Method to get List of attachments names of certain person
     *
     * @param personId  id of person
     * @return List of Strings
     */
    List<Attachment> getAttachments(int personId);

    /**
     * Method to get attachment of person
     *
     * @param personId  id of person from who to get attachment
     * @param attachmentId  id of attachment
     * @return Attachment object
     */
    Attachment getFile(int personId, int attachmentId);

    /**
     * Method to check if person has attachment with defined name
     *
     * @param personId  id of person
     * @param originalFilename  name of attachment's file
     * @return Boolean true or false
     */
    boolean isPersonHasFile(int personId, String originalFilename);


    /**
     * Method to check if person has attachment with defined name
     *
     * @param personId  id of person
     * @param attachmentId  id of attachment
     * @return Boolean true or false
     */
    boolean isPersonHasFile(int personId, int attachmentId);

    /**
     * Method to get all attachments of defined person
     *
     * @param personId  id of person,whos attachment to get
     * @return List of Attachment objects
     */
    List<Attachment> findAllAttachments(int personId);
}

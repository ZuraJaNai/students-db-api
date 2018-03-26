package no.itera.services;

import no.itera.dao.AttachmentDao;
import no.itera.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private PersonService personService;

    /**
     * Method to add file to instance of Person class
     *
     * @param personId  id of person to whom to add file
     * @param buffer  byte array containing file data
     * @param originalFilename  name of the file
     * @param contentType  content type of the file
     * @return Boolean true
     */
    @Override
    public boolean addFile(int personId, byte[] buffer, String originalFilename, String contentType) {
        Attachment attachment = new Attachment(buffer,originalFilename,contentType,personId);
        attachmentDao.save(attachment);
        personService.updateAttachments(personId,attachment);
        return true;
    }

    /**
     * Method to delete attachment from the database
     *
     * @param attachmentId  id of attachment to be deleted
     */
    @Override
    public void deleteFile(int attachmentId) {
        attachmentDao.delete(attachmentId);
    }

    /**
     * Method to get List of attachments names of certain person
     *
     * @param personId  id of person
     * @return List of Strings
     */
    @Override
    public List<Attachment> getAttachments(int personId) {
        ArrayList<Attachment> attachments = new ArrayList<>();
        Iterable<Attachment> savedAttachments = attachmentDao.findAll();
        for (Attachment attachment :
                savedAttachments) {
            if(attachment.getPersonId() == personId){
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    /**
     * Method to get attachment of person
     *
     * @param personId  id of person from who to get attachment
     * @param attachmentId  id of attachment
     * @return Attachment object
     */
    @Override
    public Attachment getFile(int personId, int attachmentId) {
        Attachment tempAttachments = null;
        List<Attachment> savedAttachments = this.findAllAttachments(personId);
        for (Attachment attachment :
                savedAttachments) {
            if(attachment.getPersonId() == personId &&
                    attachment.getId() == (attachmentId)){
                tempAttachments = attachment;
            }
        }
        return tempAttachments;
    }

    /**
     * Method to check if person has attachment with defined name
     *
     * @param personId  id of person
     * @param originalFilename  name of attachment's file
     * @return Boolean true or false
     */
    @Override
    public boolean isPersonHasFile(int personId, String originalFilename) {
        List<Attachment> savedAttachments = this.findAllAttachments(personId);
        for (Attachment attachment :
                savedAttachments) {
            if(attachment.getFilename().equals(originalFilename)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if person has attachment with defined name
     *
     * @param personId  id of person
     * @param attachmentId  id of attachment
     * @return Boolean true or false
     */
    @Override
    public boolean isPersonHasFile(int personId, int attachmentId) {
        List<Attachment> savedAttachments = this.findAllAttachments(personId);
        for (Attachment attachment :
                savedAttachments) {
            if(attachment.getId() == attachmentId){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get all attachments of defined person
     *
     * @param personId  id of person,whos attachment to get
     * @return List of Attachment objects
     */
    public List<Attachment> findAllAttachments(int personId){
        List<Attachment> attachments = new ArrayList<>();
        for (Attachment attachment :
                attachmentDao.findAll()) {
            if (attachment.getPersonId() == personId){
                attachments.add(attachment);
            }
        }
        return attachments;
    }
}

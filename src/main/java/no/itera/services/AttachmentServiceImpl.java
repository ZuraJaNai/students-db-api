package no.itera.services;

import no.itera.dao.AttachmentDao;
import no.itera.model.Attachment;
import no.itera.model.AttachmentType;
import no.itera.model.Person;
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
        Attachment attachment = new Attachment(buffer,originalFilename,
                contentType,personId, AttachmentType.DOCUMENT);
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
            if(attachment.getPersonId() == personId&&
                    attachment.getAttachmentType() != AttachmentType.PHOTO){
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

    /**
     * Method to add photo to instance of Person
     *  @param personId  id of Person to whom to add photo
     * @param bytes  the photo data
     * @param originalFilename name of file
     * @param contentType mimetype of file
     */
    @Override
    public void addPhoto(int personId, byte[] bytes, String originalFilename, String contentType){
        Person person = personService.getById(personId);
        Integer oldPhotoId = null;
        if(person.getPhoto() != null){
            oldPhotoId = person.getPhoto().getId();
        }
        Attachment photo = new Attachment(bytes,originalFilename,contentType,personId,AttachmentType.PHOTO);
        attachmentDao.save(photo);
        person.setPhoto(photo);
        if(oldPhotoId != null){
            this.deleteFile(oldPhotoId);
        }
        personService.addPerson(person);
    }

    /**
     * Method to delete photo from the instance of Person
     *
     * @param personId  id of person whos photo to delete
     */
    @Override
    public void deletePhoto(int personId){
        Person person = personService.getById(personId);
        int photoId = person.getPhoto().getId();
        person.setPhoto(null);
        attachmentDao.delete(photoId);
        personService.addPerson(person);
    }
}

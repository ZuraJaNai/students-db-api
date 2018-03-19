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

    @Override
    public boolean addFile(int personId, byte[] buffer, String originalFilename, String contentType) {
        Attachment attachment = new Attachment(buffer,originalFilename,contentType,personId);
        attachmentDao.save(attachment);
        personService.updateAttachments(personId,attachment);
        return true;
    }

    @Override
    public void deleteFile(int attachmentId) {
        attachmentDao.delete(attachmentId);
    }


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

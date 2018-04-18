package no.itera.services;

import no.itera.dao.AttachmentDao;
import no.itera.model.Attachment;
import no.itera.model.AttachmentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {

    @Mock
    private AttachmentDao daoMock;

    @Mock
    private PersonService personService;

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    @Test
    public void checkAddFile(){
        assertEquals(true,attachmentService.addFile(1,
                new byte[0],"filename","application/pdf"));
    }

    @Test
    public void checkGetAllAttachments(){
        Attachment attachment = new Attachment(new byte[0],"filename",
                "application/pdf",1, AttachmentType.DOCUMENT);
        when(daoMock.findAll()).thenReturn(Arrays.asList(attachment));
        Attachment result = attachmentService.getAttachments(1).get(0);
        assertEquals(attachment.getFilename(),result.getFilename());
        assertEquals(attachment.getMimetype(),result.getMimetype());
    }

    @Test
    public void checkGetAllAttachmentsIfHasOnlyPhoto(){
        Attachment attachment = new Attachment(new byte[0],"filename",
                "application/pdf",1, AttachmentType.PHOTO);
        when(daoMock.findAll()).thenReturn(Arrays.asList());
        assertEquals(true,attachmentService.getAttachments(1).isEmpty());
    }

    @Test
    public void checkGetFileIfExists(){
        Attachment attachment = new Attachment(new byte[0],"filename",
                "application/pdf",1, AttachmentType.DOCUMENT);
        when(daoMock.findAll()).thenReturn(Arrays.asList(attachment));
        Attachment result = attachmentService.getFile(1,attachment.getId());
        assertEquals(attachment.getFilename(),result.getFilename());
        assertEquals(attachment.getMimetype(),result.getMimetype());
        assertEquals(attachment.getId(),result.getId());
    }

    @Test
    public void checkGetFileIfNotExists(){
        when(daoMock.findAll()).thenReturn(Arrays.asList());
        Attachment result = attachmentService.getFile(1,1);
        assertEquals(null,result);
    }

    @Test
    public void checkIfPersonHasFileById(){
        Attachment attachment = new Attachment(new byte[0],"filename",
                "application/pdf",1, AttachmentType.DOCUMENT);
        when(daoMock.findAll()).thenReturn(Arrays.asList(attachment));
        assertEquals(true,
                attachmentService.isPersonHasFile(1,attachment.getId()));
    }

    @Test
    public void checkIfPersonHasFileByName(){
        Attachment attachment = new Attachment(new byte[0],"filename",
                "application/pdf",1, AttachmentType.DOCUMENT);
        when(daoMock.findAll()).thenReturn(Arrays.asList(attachment));
        assertEquals(true,
                attachmentService.isPersonHasFile(1,attachment.getFilename()));
    }
}

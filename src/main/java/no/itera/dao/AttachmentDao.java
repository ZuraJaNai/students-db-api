package no.itera.dao;

import no.itera.model.Attachment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface AttachmentDao extends CrudRepository<Attachment,Integer> {

}

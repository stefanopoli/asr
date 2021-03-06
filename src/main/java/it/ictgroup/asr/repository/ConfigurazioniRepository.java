package it.ictgroup.asr.repository;

import it.ictgroup.asr.model.Configurazione;

import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.giavacms.commons.model.Search;

@Stateless
@LocalBean
public class ConfigurazioniRepository extends BaseRepository<Configurazione>
{

   private static final long serialVersionUID = 1L;

   @Override
   protected String getDefaultOrderBy()
   {
      return " orderBy asc ";
   }

   @Override
   protected void applyRestrictions(Search<Configurazione> search, String alias,
            String separator, StringBuffer sb, Map<String, Object> params)
   {

      // id lotto;
      if (search.getObj().getId() != null)
      {
         sb.append(separator).append(alias)
                  .append(".id = :id ");
         params.put("id", search.getObj().getId());
         separator = " and ";
      }

      // tipologia flusso
      if (search.getObj().getTipologiaFlusso() != null)
      {
         sb.append(separator).append(alias)
                  .append(".tipologiaFlusso = :tipologiaFlusso ");
         params.put("tipologiaFlusso", search.getObj().getTipologiaFlusso());
         separator = " and ";
      }

      // nome
      if (search.getObj().getNome() != null && !search.getObj().getNome().trim().isEmpty())
      {
         sb.append(separator).append(alias)
                  .append(".nome LIKE :nome ");
         params.put("nome", likeParam(search.getObj().getNome()));
         separator = " and ";
      }

      // folder
      if (search.getObj().getFolder() != null && !search.getObj().getFolder().trim().isEmpty())
      {
         sb.append(separator).append(alias)
                  .append(".folder = :folder ");
         params.put("folder", likeParam(search.getObj().getFolder()));
         separator = " and ";
      }

   }

   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public List<Configurazione> getAll()
   {
      // TODO Auto-generated method stub
      return super.getAllList();
   }

}
package it.ictgroup.asr.controller;

import it.ictgroup.asr.model.Elaborazione;
import it.ictgroup.asr.repository.ElaborazioniRepository;
import it.ictgroup.asr.service.FolderService;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.giavacms.commons.annotation.EditPage;
import org.giavacms.commons.annotation.ListPage;
import org.giavacms.commons.annotation.OwnRepository;
import org.giavacms.commons.annotation.ViewPage;
import org.giavacms.commons.controller.AbstractLazyController;
import org.giavacms.commons.util.FacesMessageUtils;

@Named
@SessionScoped
public class ElaborazioniController extends AbstractLazyController<Elaborazione>
         implements Serializable
{

   private static final long serialVersionUID = 1L;

   @EditPage
   protected static final String EDIT_PAGE = "/elaborazioni/edit.xhtml";

   @ViewPage
   protected static final String VIEW_PAGE = "/elaborazioni/view.xhtml";

   @ListPage
   protected static final String LIST_PAGE = "/elaborazioni/list.xhtml";

   @Inject
   @OwnRepository(ElaborazioniRepository.class)
   ElaborazioniRepository elaborazioniRepository;

   @Inject
   FolderService folderService;

   @Override
   public String view(Object key)
   {
      setElement(getRepository().fetch(key));

      // impostazioni locali
      // da specializzare in sottoclassi
      // settaggi nel super handler
      setEditMode(false);
      setReadOnlyMode(true);
      // vista di destinazione
      return viewPage();
   }

   public String edit(Object key)
   {
      setElement(getRepository().fetch(key));

      // impostazioni locali
      // da specializzare in sottoclassi
      // settaggi nel super handler
      setEditMode(true);
      setReadOnlyMode(false);
      // vista di destinazione
      return editPage();
   }

   public String cercaPerSigla(String sigla)
   {
      reset();
      getSearch().getObj().setFileName(sigla);
      super.refreshModel();
      return listPage();
   }

   @Override
   public String delete()
   {
      Elaborazione elaborazione = (Elaborazione) getModel().getRowData();
      try
      {
         elaborazioniRepository.eliminaConRigheFlusso(elaborazione.getId());
      }
      catch (Exception e)
      {
         e.printStackTrace();
         FacesMessageUtils.addFacesMessage("Errore durante eliminazione!");
         return null;
      }
      return listPage();
   }

   public String restart()
   {
      Elaborazione elaborazione = (Elaborazione) getModel().getRowData();
      try
      {
         elaborazioniRepository.eliminaConRigheFlusso(elaborazione.getId());
      }
      catch (Exception e)
      {
         e.printStackTrace();
         FacesMessageUtils.addFacesMessage("Errore durante eliminazione!");
         return null;
      }

      try
      {
         folderService.lancia(elaborazione.getFileName(), elaborazione.getConfigurazione());
      }
      catch (Exception e)
      {
         e.printStackTrace();
         FacesMessageUtils.addFacesMessage("Errore durante lancio elaborazione!");
         return null;
      }
      return listPage();
   }

   public String elabora()
   {
      try
      {
         folderService.verifica();
         FacesMessageUtils
                  .addFacesMessage("Lancio elaborazione effettuata con successo. Attenzione: il processo puo' durare diversi minuti.");
      }
      catch (Exception e)
      {
         FacesMessageUtils.addFacesMessage("Eccezione durante il lancio!");
      }
      return null;
   }

}
package it.ictgroup.asr.service;

import it.ictgroup.asr.model.BaseFlusso;
import it.ictgroup.asr.model.Flussoa1;
import it.ictgroup.asr.model.Flussoa2;
import it.ictgroup.asr.model.Flussoa2r;
import it.ictgroup.asr.model.Flussoc1;
import it.ictgroup.asr.model.Flussoc2;
import it.ictgroup.asr.model.Flussoc2r;
import it.ictgroup.asr.model.enums.TipologiaFlusso;
import it.ictgroup.asr.repository.ElaborazioniRepository;
import it.ictgroup.asr.repository.Flussoa2Repository;
import it.ictgroup.asr.repository.Flussoa2rRepository;
import it.ictgroup.asr.repository.Flussoc2Repository;
import it.ictgroup.asr.repository.Flussoc2rRepository;
import it.ictgroup.asr.util.FlowerFileHelper;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.giavacms.commons.model.Search;
import org.jboss.ejb3.annotation.TransactionTimeout;
import org.jboss.logging.Logger;

@Stateless
public class FlussoService implements Serializable
{
   Logger logger = Logger.getLogger(this.getClass());

   private static final long serialVersionUID = 1L;

   @Inject
   ElaborazioniRepository elaborazioniRepository;

   @Inject
   Flussoa2Repository flussoa2Repository;

   @Inject
   Flussoa2rRepository flussoa2rRepository;

   @Inject
   Flussoc2Repository flussoc2Repository;

   @Inject
   Flussoc2rRepository flussoc2rRepository;

   @SuppressWarnings("rawtypes")
   private <T extends BaseFlusso> FlowerFileHelper getFlowerFileHelper(TipologiaFlusso tipologiaFlusso)
            throws Exception
   {
      switch (tipologiaFlusso)
      {
      case A1:
         return new FlowerFileHelper<Flussoa1>(Flussoa1.class);
      case A2:
         return new FlowerFileHelper<Flussoa2>(Flussoa2.class);
      case A2R:
         return new FlowerFileHelper<Flussoa2r>(Flussoa2r.class);
      case C1:
         return new FlowerFileHelper<Flussoc1>(Flussoc1.class);
      case C2:
         return new FlowerFileHelper<Flussoc2>(Flussoc2.class);
      case C2R:
         return new FlowerFileHelper<Flussoc2r>(Flussoc2r.class);
      default:
         throw new Exception();
      }
   }

   @TransactionTimeout(value = 18000, unit = TimeUnit.SECONDS)
   public <T extends BaseFlusso> void parse(TipologiaFlusso tipologiaFlusso, String nomeFile,
            String folder,
            Long idElaborazione) throws Exception
   {
      @SuppressWarnings("unchecked")
      FlowerFileHelper<T> filedReader = getFlowerFileHelper(tipologiaFlusso);
      int i = 0;
      try (Scanner scanner = new Scanner(Paths.get(folder + nomeFile), StandardCharsets.UTF_8.name()))
      {
         T flusso = null;
         String line = null;
         while (scanner.hasNextLine())
         {
            i++;
            if (i % 100 == 0)
               logger.info(tipologiaFlusso.name() + ")" + i);
            line = scanner.nextLine();
            flusso = filedReader.valorize(line);
            if (flusso != null)
            {
               flusso.getElaborazione().setId(idElaborazione);
               elaborazioniRepository.persistAsync(flusso);
            }
            else
            {
               logger.info("Eccezione: riga non valida:" + line);
            }
         }
      }
      catch (Exception e)
      {
         elaborazioniRepository.errore(idElaborazione, e.getMessage(), i);
         logger.info(tipologiaFlusso.name() + " ESEGUITO CON ERRORE: " + idElaborazione + " file: " + folder
                  + nomeFile
                  + "num righe: " + i);
         return;
      }
      elaborazioniRepository.eseguito(idElaborazione, i);
      logger.info(tipologiaFlusso.name() + " ESEGUITO: " + idElaborazione + " file: " + folder + nomeFile
               + "num righe: " + i);
   }

   public void updateRigheInErrore(Long elaborazioneId, String nomeFile, TipologiaFlusso tipologiaFlusso)
   {
      switch (tipologiaFlusso)
      {
      case A2R:
         Search<Flussoa2r> searchA2r = new Search<Flussoa2r>(Flussoa2r.class);
         searchA2r.getObj().getElaborazione().setId(elaborazioneId);
         List<Flussoa2r> flussoa2rList = flussoa2rRepository.getList(searchA2r, 0, 0);
         for (Flussoa2r flussoa2r : flussoa2rList)
         {
            flussoa2Repository.updateWithErrori(flussoa2r.getNomeFile(), flussoa2r.getRegioneAddebitante(),
                     "aziendaUsl", flussoa2r.getCodiceIstituto(),
                     flussoa2r.getNumeroDellaScheda(), flussoa2r.getErr01(), flussoa2r.getErr02(),
                     flussoa2r.getErr03(), flussoa2r.getErr04(), flussoa2r.getErr05(), flussoa2r.getErr06(),
                     flussoa2r.getErr07(), flussoa2r.getErr08(), flussoa2r.getErr09(), flussoa2r.getErr10());
         }
         break;
      case C2R:
         Search<Flussoc2r> searchC2r = new Search<Flussoc2r>(Flussoc2r.class);
         searchC2r.getObj().getElaborazione().setId(elaborazioneId);
         List<Flussoc2r> flussoc2rList = flussoc2rRepository.getList(searchC2r, 0, 0);
         for (Flussoc2r flussoc2r : flussoc2rList)
         {
            flussoc2Repository.updateWithErrori(flussoc2r.getNomeFile(), flussoc2r.getRegioneAddebitante(),
                     flussoc2r.getCodiceStrutturaErogante(),
                     flussoc2r.getProgressivoRigaPerRicetta(), flussoc2r.getErr01(), flussoc2r.getErr02(),
                     flussoc2r.getErr03(), flussoc2r.getErr04(), flussoc2r.getErr05(), flussoc2r.getErr06(),
                     flussoc2r.getErr07(), flussoc2r.getErr08(), flussoc2r.getErr09(), flussoc2r.getErr10());
         }
         break;

      default:
         logger.info("non ho trovato il flusso corrispondente");
      }

   }
}

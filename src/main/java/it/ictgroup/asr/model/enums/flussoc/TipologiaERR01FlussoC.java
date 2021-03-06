package it.ictgroup.asr.model.enums.flussoc;

public enum TipologiaERR01FlussoC
{
   NESSUN_ERRORE ("0", "nessun errore"),
   ID_UTENTE_ERR ("1", "identificativo utente assente/errato"),
   UTENTE_NON_IN_ANAGRAFICA ("2", "utente assente da anagrafe residenti"),

   // Segnalazioni di value nell’ambito dei controlli regionali:
   CR_NESSUN_ERRORE ("A", "nessun errore"),
   CR_CF_ASSENTE ("B", "'codice fiscale dell’utente' assente o errato"),

   // Nel caso di soggetti assicurati da istituzioni estere:
   IE_DATA_NASCITA_ASSENTE ("C", "'data di nascita dell’utente' assente o errata"),
   IE_STATO_ESTERO_ASSENTE ("D", "'stato estero risultante nella TEAM' assente"),
   IE_ISTITUZ_COMPETENTE_ASSENTE ("E", "'Istituzione competente' assente"),
   IE_ID_PERSONALE_ASSENTE ("F", "'Numero identificazione personale' assente"),
   IE_ID_TESSERA_ASSENTE ("G", "'Numero Identificativo Tessera' assente"),
   IE_SCADENZA_TESSERA_ASSENTE ("H", "'Scadenza Tessera' assente");

   private String value;
   private String description;

   TipologiaERR01FlussoC(String value, String description)
   {
      this.value = value;
      this.description = description;
   }

   public String getValue()
   {
      return value;
   }

   public String getDescription()
   {
      return description;
   }
}

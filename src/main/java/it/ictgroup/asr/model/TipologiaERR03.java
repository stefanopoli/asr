package it.ictgroup.asr.model;

public enum TipologiaERR03
{
   NESSUN_ERRORE ("0"), // nessun errore
   COD_NOMENCLATORE_ASSENTE ("1"), // codice nomenclatore assente o non previsto nel dominio (solo sulle righe zn)
   COD_PRESTAZIONE_ASSENTE ("2"), // codice prestazione assente o non previsto nel dominio (solo sulle righe zn)

   // Segnalazioni di errore nell’ambito dei controlli regionali:
   // Controlli effettuati solo sulle righe da 01 a 98
   CR_NESSUN_ERRORE ("A"), // nessun errore
   CR_COD_NOMENCLATORE_ASSENTE ("B"), // “codifica nomenclatore” assente o errata
   CR_COD_PRESTAZIONE_ASSENTE ("C"), // “codice della prestazione erogata” assente o errato
   CR_COD_BRANCA_ASSENTE ("D"), // “codice branca specialistica della prestazione erogata” assente o errato
   CR_COD_SPECIALITA_ASSENTE ("E"), // “codice specialità della prestazione erogata” assente o errata
   CR_TARIFFA_ASSENTE ("F"), // “tariffa della prestazione erogata” assente o errata

   // Segnalazioni di errore da segnalare su tutto il set ricetta
   RIC_ACCESSO_PRESTAZIONI_ASSENTE ("G"), // “modalità di accesso alle prestazioni” assente o errato
   RIC_ONERE_ASSENTE ("H"); // “onere della prestazione” assente o errato

   private String errore;

   TipologiaERR03(String errore)
   {
      this.errore = errore;
   }
}

package it.ictgroup.asr.model;

public enum TipologiaModalitaCompilazRicetta
{

   COMPIL_MANUALMENTE ("1"), // ricetta compilata manualmente
   INFORMATIZZATA_NO_CF ("2"), // ricetta informatizzata con presenza della biffatura “Stampa PC”, ma assenza del codice fiscale in modalità barcode
   INFORMATIZZATA_CON_CF ("3"); // ricetta informatizzata con presenza della biffatura “Stampa PC” e del codice fiscale in modalità barcode

   private String modalita;

   TipologiaModalitaCompilazRicetta(String modalita)
   {
      this.modalita = modalita;
   }
}

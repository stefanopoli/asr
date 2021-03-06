package it.ictgroup.asr.model.enums.flussoa;

public enum TipologiaERR09FlussoA
{
   NESSUN_ERRORE ("0", "nessun errore"),
   IMPORTO_DIVERSO ("1", "importo diverso da zero per onere della degenza non compensabile (codici onere 3, 4, 9)"),
   ONERE_DEGENZA ("2", "onere della degenza assente/errato");

   private String value;
   private String description;

   TipologiaERR09FlussoA(String value, String description)
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

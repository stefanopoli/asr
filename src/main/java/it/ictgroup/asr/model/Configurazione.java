package it.ictgroup.asr.model;

import it.ictgroup.asr.model.enums.TipologiaFlusso;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Configurazione implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private Long id;
   private String nome;
   private TipologiaFlusso tipologiaFlusso;
   private String folder;
   private int orderBy;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   @Enumerated(EnumType.STRING)
   public TipologiaFlusso getTipologiaFlusso()
   {
      return tipologiaFlusso;
   }

   public void setTipologiaFlusso(TipologiaFlusso tipologiaFlusso)
   {
      this.tipologiaFlusso = tipologiaFlusso;
   }

   public String getFolder()
   {
      return folder;
   }

   public void setFolder(String folder)
   {
      this.folder = folder;
   }

   public int getOrderBy()
   {
      return orderBy;
   }

   public void setOrderBy(int orderBy)
   {
      this.orderBy = orderBy;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

}

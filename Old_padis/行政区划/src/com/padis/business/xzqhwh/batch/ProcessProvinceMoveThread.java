package com.padis.business.xzqhwh.batch;

import ctais.services.data.DataWindow;
import ctais.services.log.Log;
import ctais.services.log.LogManager;
import java.util.Date;

public class ProcessProvinceMoveThread extends Thread
{
  private int runminutes;
  private String proxzqh;

  public ProcessProvinceMoveThread(int runminutes, String proxzqh)
  {
    this.runminutes = runminutes;
    this.proxzqh = proxzqh;
  }

  public void run()
  {
    LogManager.getLogger().error("[ProcessProvinceMoveThread] run() " + this.runminutes + "...........privinceCode:" + this.proxzqh + "  start....." + new Date());

    StringBuffer sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set qhzt_dm='20' where xzqh_dm like '");
    sqlupywsjqy.append(this.proxzqh);
    sqlupywsjqy.append("'");
    sqlupywsjqy.toString();
    try
    {
      DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
      dw.update(false);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set kssj=sysdate where xzqh_dm like '");
    sqlupywsjqy.append(this.proxzqh);
    sqlupywsjqy.append("'");
    sqlupywsjqy.toString();
    try
    {
      DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
      dw.update(false);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    try
    {
      BatchPro bp = new BatchPro();

      LogManager.getLogger().error("[ProcessProvinceMoveThread] run() start " + this.proxzqh + new Date());
      bp.processPrivinceMove(this.runminutes, this.proxzqh);
      LogManager.getLogger().error("[ProcessProvinceMoveThread] run() end " + this.proxzqh + new Date());

      sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set qhzt_dm='30' where xzqh_dm like '");
      sqlupywsjqy.append(this.proxzqh);
      sqlupywsjqy.append("'");
      sqlupywsjqy.toString();
      try
      {
        DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
        dw.update(false);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

      sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set jssj=sysdate where xzqh_dm like '");
      sqlupywsjqy.append(this.proxzqh);
      sqlupywsjqy.append("'");
      sqlupywsjqy.toString();
      try
      {
        DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
        dw.update(false);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    catch (Exception e)
    {
      sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set qhzt_dm='31' where xzqh_dm like '");
      sqlupywsjqy.append(this.proxzqh);
      sqlupywsjqy.append("'");
      sqlupywsjqy.toString();
      try
      {
        DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
        dw.update(false);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

      sqlupywsjqy = new StringBuffer("update xzqh_ywsjqy set jssj=sysdate where xzqh_dm like '");
      sqlupywsjqy.append(this.proxzqh);
      sqlupywsjqy.append("'");
      sqlupywsjqy.toString();
      try
      {
        DataWindow dw = DataWindow.dynamicCreate(sqlupywsjqy.toString());
        dw.update(false);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

      LogManager.getLogger().error("[ProcessProvinceMoveThread] run() error end:" + e.getMessage());
      LogManager.getLogger().log(e);

      e.printStackTrace();
    }
  }
}
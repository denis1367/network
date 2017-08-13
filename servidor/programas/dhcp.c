#include <mysql/mysql.h>
#include <stdio.h>
#include <string.h>
 

char* concat(const char *s1, const char *s2)
{
  char *result = malloc(strlen(s1)+strlen(s2)+1);//+1 for the zero-terminator
  //in real code you would check for errors in malloc here
  strcpy(result, s1);
  strcat(result, s2);
  return result;
}


int main ()
{
  FILE * fp;

  /* open the file for writing*/
  fp = fopen ("/etc/dhcp/dhcpd.conf","w");

  char *server = "187.84.37.87";
  char *user = "root";
  char *password = "toor";
  char *database = "dhcp";

  /*VARIAVEIS QUERRY BASE*/
  MYSQL *conn;
  MYSQL_RES *res;
  MYSQL_ROW row;

  MYSQL_ROW row_1;
  MYSQL_ROW row_2;
  MYSQL_ROW row_3;
  MYSQL_ROW row_4;
  MYSQL_ROW row_5;

  conn = mysql_init(NULL);

  /* Connect to database */
  if (!mysql_real_connect(conn, server,user, password, database, 0, NULL, 0)) 
  {
    fprintf (stderr, "%s\n", mysql_error(conn));
    exit(1);
  }

  fprintf (fp,"#Arquivo de configuração do DHCP Server");
  fprintf (fp, "%s\n\n"," ");

  mysql_query(conn,"SELECT option_opcaodhcp, valor_opcaodhcp FROM dhcpopc WHERE ativo_opcaodhcp = 1");
  MYSQL_RES *confres = mysql_store_result(conn);
  int totalrows = mysql_num_rows(confres);
  int numfields = mysql_num_fields(confres);
      

  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {
      char *val = row[i];
      fprintf (fp,"%s", val);

      if (i < 1) {
        fprintf (fp,"%s"," ");
      }
    }

    fprintf (fp,"%s\n", ";");
  }

  fprintf (fp,"%s\n","");
  fprintf (fp,"%s\n\n", "# Planos de velocidade dos usuarios");

  mysql_query(conn,"SELECT nome_plano, arqbinario_plano, nserver_plano FROM plano WHERE ativo_plano = 1");
  confres = mysql_store_result(conn);
  totalrows = mysql_num_rows(confres);
  numfields = mysql_num_fields(confres);


  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {

      char *val = row[i];

      if (i == 0)
      {
        fprintf (fp,"%s", "Class \"" );
        
        fprintf (fp,"%s", val);
        fprintf (fp,"%s\n","\" {");
        fprintf (fp,"%s\n", "\tmatch pick-first-value (hardware,option dhcp-client-identifier);");
      } 

      if (i==1)
      {
        if (!(val == NULL))
        {
          fprintf (fp,"%s", "\tfilename \"" );
          fprintf (fp,"%s", val);
          fprintf (fp,"%s\n", "\";");
        }
        
      }

      if (i==2)
      {
        if (!(val == NULL))
        {
          fprintf (fp,"%s", "\tnext-server " );
          fprintf (fp,"%s", val);
          fprintf (fp,"%s\n", ";");
        }
      }
      
    }

    fprintf (fp,"%s\n\n", "}");

  }


  mysql_query(conn,"SELECT CONCAT(rede_subrede,' netmask ',netmask_subrede) AS chave, cod_subrede from subrede WHERE gerencia_subrede = 1");
  confres = mysql_store_result(conn);
  totalrows = mysql_num_rows(confres);
  numfields = mysql_num_fields(confres);


  fprintf(fp, "%s\n\n", "# REDE DO DHCP");

  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {

      char *val = row[i];

      if (i == 0)
      {
        fprintf (fp,"%s","\tsubnet ");
        fprintf (fp,"%s", val);
        fprintf (fp,"%s\n\n"," { }" );
      } 
    }
  }

  /*fprintf (fp,"%s\n\n", "# Rede reservada para servidores\n\nsubnet 172.17.17.0 netmask 255.255.255.248 { }");
 
  mysql_query(conn,concat("SELECT CONCAT(rede_subrede,' netmask ',netmask_subrede) AS chave, cod_subrede from subrede WHERE gerencia_subrede = 1", val));*/

  fprintf (fp,"%s\n", "# Rede dos modem e clientes #\n\n########## CMTS_CISCO_1 ##########");


  mysql_query(conn,"SELECT nome_areacmts, cod_areacmts FROM areacmts WHERE ativo_areacmts = 1");
  confres = mysql_store_result(conn);
  totalrows = mysql_num_rows(confres);
  numfields = mysql_num_fields(confres);

  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {

      char *val = row[i];


      if (i == 0)
      {
        fprintf (fp,"%s", "shared-network " );
        fprintf (fp,"%s", val);
        fprintf (fp,"%s\n"," {");
      } 

      if (i == 1)
      {
        mysql_query(conn,concat("SELECT CONCAT(rede_subrede,' netmask ',netmask_subrede) AS chave, cod_subrede from subrede WHERE ativo_subrede = 1 AND gerencia_subrede = 0 AND cod_areacmts =", val));
        MYSQL_RES *confres_1 = mysql_store_result(conn);
        int totalrows_1 = mysql_num_rows(confres_1);
        int numfields_1 = mysql_num_fields(confres_1);

        while((row_1 = mysql_fetch_row(confres_1)))
        {
          for(int i = 0; i < numfields_1; i++)
          {

            char *val_1 = row_1[i];

            if (i == 0)
            {
              fprintf (fp,"%s","\tsubnet ");
              fprintf (fp,"%s", val_1);
              fprintf (fp,"%s\n"," {" );
 
            } 

            if (i == 1)
            {
              mysql_query(conn,concat("SELECT CONCAT(nome_subredeopc, ' ',valor_subredeopc, ';') FROM subredeopc WHERE ativo_subredeopc=1 AND cod_subrede =", val_1));
              MYSQL_RES *confres_2 = mysql_store_result(conn);
              int totalrows_2 = mysql_num_rows(confres_2);
              int numfields_2 = mysql_num_fields(confres_2);

              while((row_2 = mysql_fetch_row(confres_2)))
              {
                for(int i = 0; i < numfields_2; i++)
                {

                  char *val_2 = row_2[i];

                  if (i == 0)
                  {
                    fprintf (fp,"%s","\t ");
                    fprintf (fp,"%s\n", val_2);

       
                  } 
                }
              }

              mysql_query(conn,concat(" SELECT cable_poolopc, cod_poolsubrede FROM poolsubrede WHERE ativo_poolsubrede = 1 AND cod_subrede=", val_1));
              MYSQL_RES *confres_3 = mysql_store_result(conn);
              int totalrows_3 = mysql_num_rows(confres_3);
              int numfields_3 = mysql_num_fields(confres_3);

              while((row_3 = mysql_fetch_row(confres_3)))
              {
                for(int i = 0; i < numfields_3; i++)
                {

                  char *val_3 = row_3[i];

                  if (i == 0)
                  {
                    

                    if (strcmp (val_3, "1") == 0)
                    {
                      
                      
                    
                      mysql_query(conn,"SELECT nome_plano FROM plano WHERE cpe = 0");
                      MYSQL_RES *confres_4 = mysql_store_result(conn);
                      int totalrows_4 = mysql_num_rows(confres_4);
                      int numfields_4 = mysql_num_fields(confres_4);

                      fprintf (fp,"%s\n", "");
                      fprintf (fp,"%s\t", "");
                      fprintf (fp,"%s\n", "pool {");

                      while((row_4 = mysql_fetch_row(confres_4)))
                      {


                        for(int i = 0; i < numfields_4; i++)
                        {

                          char *val_4 = row_4[i];

                          if (i == 0)
                          {


                            fprintf (fp,"%s","\t ");
                            fprintf (fp,"%s", "allow members of \"");
                            fprintf (fp,"%s", val_4);
                            fprintf (fp,"%s\n","\";" );

               
                          }

                        }

                        
                      }

                      mysql_query(conn,"SELECT nome_plano FROM plano WHERE cpe = 1");
                      confres_4 = mysql_store_result(conn);
                      totalrows_4 = mysql_num_rows(confres_4);
                      numfields_4 = mysql_num_fields(confres_4);


                      while((row_4 = mysql_fetch_row(confres_4)))
                      {


                        for(int i = 0; i < numfields_4; i++)
                        {

                          char *val_4 = row_4[i];

                          if (i == 0)
                          {


                            fprintf (fp,"%s","\t ");
                            fprintf (fp,"%s", "deny members of \"");
                            fprintf (fp,"%s", val_4);
                            fprintf (fp,"%s\n","\";" );

               
                          }

                        }

                        
                      }


                      
                    } else 

                    {

                      mysql_query(conn,"SELECT nome_plano FROM plano WHERE cpe = 1");
                      MYSQL_RES *confres_5 = mysql_store_result(conn);
                      int totalrows_5 = mysql_num_rows(confres_5);
                      int numfields_5 = mysql_num_fields(confres_5);

                      fprintf (fp,"%s\n", "");
                      fprintf (fp,"%s\t", "");
                      fprintf (fp,"%s\n", "pool {");

                      while((row_5 = mysql_fetch_row(confres_5)))
                      {


                        for(int i = 0; i < numfields_5; i++)
                        {

                          char *val_5 = row_5[i];

                          if (i == 0)
                          {


                            fprintf (fp,"%s","\t ");
                            fprintf (fp,"%s", "allow members of \"");
                            fprintf (fp,"%s", val_5);
                            fprintf (fp,"%s\n","\";" );

               
                          }

                        }

                        
                      }

                    mysql_query(conn,"SELECT nome_plano FROM plano WHERE cpe = 0");
                      confres_5 = mysql_store_result(conn);
                      totalrows_5 = mysql_num_rows(confres_5);
                      numfields_5 = mysql_num_fields(confres_5);


                      while((row_5 = mysql_fetch_row(confres_5)))
                      {


                        for(int i = 0; i < numfields_5; i++)
                        {

                          char *val_5 = row_5[i];

                          if (i == 0)
                          {


                            fprintf (fp,"%s","\t ");
                            fprintf (fp,"%s", "deny members of \"");
                            fprintf (fp,"%s", val_5);
                            fprintf (fp,"%s\n","\";" );

               
                          }

                        }

                        
                      }

                    } 

                  }

                  if (i == 1)
                  {
                    mysql_query(conn,concat("SELECT CONCAT(nome_poolopc, ' ', valor_poolopc, ';') FROM poolopc WHERE ativo_poolopc =1 AND cod_poolsubrede =", val_3));
                    MYSQL_RES *confres_4 = mysql_store_result(conn);
                    int totalrows_4 = mysql_num_rows(confres_4);
                    int numfields_4 = mysql_num_fields(confres_4);


                    while((row_4 = mysql_fetch_row(confres_4)))
                    {


                      for(int i = 0; i < numfields_4; i++)
                      {

                        char *val_4 = row_4[i];

                        if (i == 0)
                        {

                          fprintf (fp,"%s","\t ");
                          fprintf (fp,"%s\n", val_4);

             
                        } 



                      }

                      
                     }

                    fprintf (fp,"%s\n\n", "\t}###############VC EH POOL?"); /*FECHA POOL*/

                    
                  } 

                
                }
              }
            }
          }
          fprintf (fp,"%s\n\n", "\t}###############VOCE EH SUBREDE?"); /*FECHA SUBREDE*/
        }

      }

    }

    fprintf (fp,"%s\n\n","}###############VC EH AREA?"); /*FECHA AREA*/
  }

  mysql_query(conn,"SELECT B.nome_cliente, A.mac, A.ip FROM reserva as A INNER JOIN cliente as B ON A.cod_cliente = B.cod_cliente");
  confres = mysql_store_result(conn);
  totalrows = mysql_num_rows(confres);
  numfields = mysql_num_fields(confres);


  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {

      char *val = row[i];

      if (i == 0)
      {
        fprintf (fp,"%s", "host " );        
        fprintf (fp,"%s", val);
        fprintf (fp,"%s", " { hardware ethernet ");
      }

      if (i == 1)
      {
        fprintf (fp,"%s", val);
        fprintf (fp,"%s", "; fixed-address ");
      }

      if (i == 2)
      {
        fprintf (fp,"%s", val);
        fprintf (fp,"%s\n", "; }");
      }
    }
  } 

  fprintf (fp,"%s\n", "");

  mysql_query(conn,"SELECT B.nome_plano, A.mac_cable FROM cable as A INNER JOIN plano AS B ON A.cod_plano = B.cod_plano");
  confres = mysql_store_result(conn);
  totalrows = mysql_num_rows(confres);
  numfields = mysql_num_fields(confres);


  while((row = mysql_fetch_row(confres)))
  {
    for(int i = 0; i < numfields; i++)
    {

      char *val = row[i];

      if (i == 0)
      {
        fprintf (fp,"%s", "subclass \"" );        
        fprintf (fp,"%s", val);
        fprintf (fp,"%s", "\" ");
      }

      if (i == 1)
      {
        fprintf (fp,"%s", val);
        fprintf (fp,"%s\n", ";");
      }
    }
  }

  /* close connection */
  mysql_free_result(res);
  mysql_close(conn);

  fclose (fp);
  return 0;
}

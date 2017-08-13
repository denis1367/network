#include <stdio.h>    /* standard in and output*/
#include <sys/socket.h> /* for socket() and socket functions*/
#include <arpa/inet.h>  /* for sockaddr_in and inet_ntoa() */
#include <stdlib.h>    
#include <string.h>     
#include <unistd.h>     /* for close() */
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

char* concat(const char *s1, const char *s2)
{
  char *result = malloc(strlen(s1)+strlen(s2)+1);//+1 for the zero-terminator
  //in real code you would check for errors in malloc here
  strcpy(result, s1);
  strcat(result, s2);
  return result;
}
 
 
int main(int argc, char *argv[]){
    int sock, connected, bytes_received, true = 1;  
    char recv_data;      
    char replyBuffer[32];
        
    struct sockaddr_in server_addr,client_addr;    
    int sin_size;       
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        perror("Socket");
        exit(1);
    }
     
    if (setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&true,sizeof(int)) == -1) {
        perror("Setsockopt");
        exit(1);
    }
         
    server_addr.sin_family = AF_INET;         
    server_addr.sin_port = htons(2400);     
    server_addr.sin_addr.s_addr = INADDR_ANY; 
    bzero(&(server_addr.sin_zero),8); 
 
    if (bind(sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Unable to bind");
            exit(1);
    }
 
    if (listen(sock, 5) == -1) {
        perror("Listen");
        exit(1);
    }
         
    //printf("\nTCPServer Waiting for client on port 2400");
 
   while(1){  
        sin_size = sizeof(client_addr);
        connected = accept(sock, (struct sockaddr *)&client_addr,&sin_size);
        //printf("\n Got a connection from (%s , %d)",inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));
        //printf("%s\n","RECEBIDO!");
        char *aInt[4];
            

        char var[20];
        time_t now = time(NULL);
        
        strftime(var, 20, "%Y-%m-%d_%H:%M:%S", localtime(&now));
        printf("%s\n", concat("Solicitation Received ", var));


        strftime(var, 20, "%Y%m%d_%H:%M:%S", localtime(&now));
        system(concat("mv /etc/dhcp/dhcpd.conf /backup/dhcp/dhcpd_",concat(var,".bkp")));

        system("/etc/init.d/isc-dhcp-server stop");
        system("/servidor/programas/dhcp");
        system("/etc/init.d/isc-dhcp-server start");

 
 
        while ((bytes_received = recv(connected,&recv_data,1,0)) > 0){
        //printf("\nrecv= %c\n", recv_data);
        }
        int success = 1;
        sprintf(replyBuffer, "%d", success);    
        //printf("reply buffer = %s\n", replyBuffer);
        if (send(connected, replyBuffer, strlen(replyBuffer), 0) == -1)
                perror("send() failed"); 
                success = 0;
        close(connected);
    }
}

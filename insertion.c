#include<stdio.h>
#include<string.h>
void main()
{
    char a[]={10,20,30,40,50,60,70};int n=7,key=35,i=0,temp;
    while(i<n)
       if(a[i]<key)
        i++;

    if(i==n-1)
    {
        a[n]=key;
        n=n+1;
    }
    else
    {
        temp=a[i];
        a[i]=key;
        while(i<n-1)
        {
            i++;
            a[i]=temp;
            a[i]=a[i+1];
        }
        n=n+1;
    }
        int l=strlen(a);
    i=0;
    while(i<l)
          {
             printf("\t%d",a[i]);
          }
}

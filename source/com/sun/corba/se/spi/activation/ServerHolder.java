package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/ServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from c:/jenkins/workspace/8-2-build-windows-amd64-cygwin/jdk8u271/605/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Wednesday, September 16, 2020 7:17:08 PM UTC
*/


/** Server callback API, passed to Activator in active method.
    */
public final class ServerHolder implements org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.spi.activation.Server value = null;

  public ServerHolder ()
  {
  }

  public ServerHolder (com.sun.corba.se.spi.activation.Server initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.spi.activation.ServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.spi.activation.ServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.spi.activation.ServerHelper.type ();
  }

}

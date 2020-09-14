/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.resource.spi;

import javax.security.auth.Subject;
import java.util.Set;
import jakarta.resource.ResourceException;
import jakarta.resource.NotSupportedException;

/** 
 * ManagedConnectionFactory instance is a factory of both ManagedConnection
 *  and EIS-specific connection factory instances. This interface supports 
 *  connection pooling by providing methods for matching and creation of
 *  ManagedConnection instance. A ManagedConnectionFactory 
 *  instance is required to be a JavaBean.
 *
 *  @version     0.6
 *  @author      Rahul Sharma
 *
 *  @see         jakarta.resource.spi.ManagedConnection
 */

public interface ManagedConnectionFactory extends java.io.Serializable {

    /**
     * Creates a Connection Factory instance. The Connection Factory
     *  instance gets initialized with the passed ConnectionManager. In
     *  the managed scenario, ConnectionManager is provided by the 
     *  application server.
     *
     *  @param    cxManager    ConnectionManager to be associated with
     *                         created EIS connection factory instance
     *  @return   EIS-specific Connection Factory instance or
     *            jakarta.resource.cci.ConnectionFactory instance
     *   
     *  @throws   ResourceException     Generic exception
     *  @throws   ResourceAdapterInternalException
     *                Resource adapter related error condition
     */
    public Object createConnectionFactory(ConnectionManager cxManager)
	throws ResourceException;

    /**
     * Creates a Connection Factory instance. The Connection Factory 
     *  instance gets initialized with a default ConnectionManager provided
     *  by the resource adapter.
     *
     *  @return   EIS-specific Connection Factory instance or
     *            jakarta.resource.cci.ConnectionFactory instance
     *
     *  @throws   ResourceException     Generic exception
     *  @throws   ResourceAdapterInternalException
     *                Resource adapter related error condition
     */
    public Object createConnectionFactory() throws ResourceException;

 
    /** 
     * Creates a new physical connection to the underlying EIS 
     *  resource manager.
     *
     *  <p>ManagedConnectionFactory uses the security information (passed as
     *  Subject) and additional ConnectionRequestInfo (which is specific to
     *  ResourceAdapter and opaque to application server) to create this new
     *  connection.
     *
     *  @param   subject        Caller's security information
     *  @param   cxRequestInfo  Additional resource adapter specific connection
     *                          request information
     *
     *  @throws  ResourceException     generic exception
     *  @throws  SecurityException     security related error
     *  @throws  ResourceAllocationException
     *                                 failed to allocate system resources for
     *                                 connection request
     *  @throws  ResourceAdapterInternalException
     *                                 resource adapter related error condition
     *  @throws  EISSystemException    internal error condition in EIS instance
     *
     *  @return  ManagedConnection instance
     */
    public ManagedConnection createManagedConnection(
			      Subject subject, 
			      ConnectionRequestInfo cxRequestInfo) 
	throws ResourceException;

    /** 
     * Returns a matched connection from the candidate set of connections. 
     *  
     *  
     *  <p>ManagedConnectionFactory uses the security info (as in Subject)
     *  and information provided through ConnectionRequestInfo and additional
     *  Resource Adapter specific criteria to do matching. Note that criteria
     *  used for matching is specific to a resource adapter and is not
     *  prescribed by the Connector specification.</p>
     *
     *  <p>This method returns a ManagedConnection instance that is the best 
     *  match for handling the connection allocation request.</p>
     *
     *  @param   connectionSet   candidate connection set
     *  @param   subject         caller's security information
     *  @param   cxRequestInfo   additional resource adapter specific 
     *                           connection request information  
     *
     *  @throws  ResourceException     generic exception
     *  @throws  SecurityException     security related error
     *  @throws  ResourceAdapterInternalException
     *                                 resource adapter related error condition
     *  @throws  NotSupportedException if operation is not supported
     *
     *  @return  ManagedConnection     if resource adapter finds an
     *                                 acceptable match otherwise null
     **/
    public ManagedConnection matchManagedConnections(
				      Set connectionSet,
				      Subject subject,
				      ConnectionRequestInfo  cxRequestInfo) 
	throws ResourceException;

    /** 
     * <p>Set the log writer for this ManagedConnectionFactory instance.</p>
     *
     *  <p>The log writer is a character output stream to which all logging and
     *  tracing messages for this ManagedConnectionfactory instance will be 
     *  printed.</p>
     *
     * <p>ApplicationServer manages the association of output stream with the
     *  ManagedConnectionFactory. When a ManagedConnectionFactory object is 
     *  created the log writer is initially null, in other words, logging is 
     *  disabled. Once a log writer is associated with a
     *  ManagedConnectionFactory, logging and tracing for 
     *  ManagedConnectionFactory instance is enabled.</p>
     *
     *  <p>The ManagedConnection instances created by ManagedConnectionFactory
     *  "inherits" the log writer, which can be overridden by ApplicationServer
     *  using ManagedConnection.setLogWriter to set ManagedConnection specific
     *  logging and tracing.</p>
     *
     *  @param   out                   PrintWriter - an out stream for
     *                                 error logging and tracing
     *  @throws  ResourceException     generic exception
     *  @throws  ResourceAdapterInternalException
     *                                 resource adapter related error condition
     */
    public void setLogWriter(java.io.PrintWriter out) throws ResourceException;

    /** 
     * Get the log writer for this ManagedConnectionFactory instance.
     *
     *  <p>The log writer is a character output stream to which all logging and
     *  tracing messages for this ManagedConnectionFactory instance will be 
     *  printed</p>
     *
     *  <p>ApplicationServer manages the association of output stream with the
     *  ManagedConnectionFactory. When a ManagedConnectionFactory object is 
     *  created the log writer is initially null, in other words, logging is 
     *  disabled.</p>
     *
     *  @return  PrintWriter
     *  @throws  ResourceException     generic exception
     */
    public java.io.PrintWriter getLogWriter() throws ResourceException;

    /** 
     * Returns the hash code for the ManagedConnectionFactory
     * 
     *  @return  hash code for the ManagedConnectionFactory
     */
    @Override
    public int hashCode();

    /** 
     * Check if this ManagedConnectionFactory is equal to another
     * ManagedConnectionFactory.
     *  @param other to check
     *  @return  true if two instances are equal
     */
    @Override
    public boolean equals(Object other);
}

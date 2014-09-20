package org.onlab.onos.net.host;

import java.util.Set;

import org.onlab.onos.net.ConnectPoint;
import org.onlab.onos.net.DeviceId;
import org.onlab.onos.net.Host;
import org.onlab.onos.net.HostId;
import org.onlab.packet.IpPrefix;
import org.onlab.packet.MacAddress;
import org.onlab.packet.VlanId;

/**
 * Service for interacting with the inventory of end-station hosts.
 */
public interface HostService {

    /**
     * Returns the number of end-station hosts known to the system.
     *
     * @return number of end-station hosts
     */
    public int getHostCount();

    /**
     * Returns a collection of all end-station hosts.
     *
     * @return collection of hosts
     */
    Iterable<Host> getHosts();

    /**
     * Returns the host with the specified identifier.
     *
     * @param hostId host identifier
     * @return host or null if one with the given identifier is not known
     */
    Host getHost(HostId hostId);

    /**
     * Returns the set of hosts that belong to the specified VLAN.
     *
     * @param vlanId vlan identifier
     * @return set of hosts in the given vlan id
     */
    Set<Host> getHostsByVlan(VlanId vlanId);

    /**
     * Returns the set of hosts that have the specified MAC address.
     *
     * @param mac mac address
     * @return set of hosts with the given mac
     */
    Set<Host> getHostsByMac(MacAddress mac);

    /**
     * Returns the set of hosts that have the specified IP address.
     *
     * @param ip ip address
     * @return set of hosts with the given IP
     */
    Set<Host> getHostsByIp(IpPrefix ip);

    // TODO: consider adding Host getHostByIp(IpAddress ip, VlanId vlan);

    /**
     * Returns the set of hosts whose most recent location is the specified
     * connection point.
     *
     * @param connectPoint connection point
     * @return set of hosts connected to the connection point
     */
    Set<Host> getConnectedHosts(ConnectPoint connectPoint);

    /**
     * Returns the set of hosts whose most recent location is the specified
     * infrastructure device.
     *
     * @param deviceId device identifier
     * @return set of hosts connected to the device
     */
    Set<Host> getConnectedHosts(DeviceId deviceId);

    /**
     * Requests the host service to monitor hosts with the given IP address and
     * notify listeners of changes.
     *
     * @param ip IP address of the host to monitor
     */
    void monitorIp(IpPrefix ip);

    /**
     * Stops the host service from monitoring an IP address.
     *
     * @param ip IP address to stop monitoring
     */
    // TODO clients can cancel other client's requests
    void stopMonitoringIp(IpPrefix ip);

    /**
     * Adds the specified host listener.
     *
     * @param listener host listener
     */
    void addListener(HostListener listener);

    /**
     * Removes the specified host listener.
     *
     * @param listener host listener
     */
    void removeListener(HostListener listener);

}

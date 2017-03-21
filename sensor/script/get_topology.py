import os
import re
import redis
import json
from sys import argv
import restclient
import nmap
from xml.dom.minidom import parse
import xml.dom.minidom

#read input
if len(argv) != 6:
    print "The input is not right.\nFirst is sensor ip;Second is mask;Third is monitor Ip;Forth is monitor port;fifth is sensor id."
sensor_id = argv[1]
ip = argv[2]
mask = argv[3]
monitor_ip = argv[4]
monitor_port = argv[5]
#scan line
scanner = nmap.PortScanner()
scanner.scan(hosts = monitor_ip, arguments="--traceroute")
scan_result_xml = scanner.get_nmap_last_output()
dom_tree = xml.dom.minidom.parseString(scan_result_xml)
hops = dom_tree.documentElement.getElementsByTagName("hop")
line = []
print "trace:"
gateway = hops[0].getAttribute("ipaddr")

for hop in hops:
    host = hop.getAttribute("ipaddr")
    print "{0}".format(host)
    if host != monitor_ip:
        line.append(host)
if gateway == monitor_ip:
    scanner = nmap.PortScanner()
    scanner.scan(hosts="www.baidu.com", arguments="--traceroute")
    scan_result_xml = scanner.get_nmap_last_output()
    dom_tree = xml.dom.minidom.parseString(scan_result_xml)
    hops = dom_tree.documentElement.getElementsByTagName("hop")
    gateway = hops[0].getAttribute("ipaddr")
#register sensor
sensor_info = dict()
sensor_info["ip"] = ip
sensor_info["mask"] = mask
sensor_info["line"] = line
sensor_info["id"] = sensor_id
restclient.POST("http://{0}:{1}/sensors".format(monitor_ip, monitor_port), params=sensor_info, headers={'Content-Type': 'application/json'}, async=False)
print "register sensor {0}/{1} in monitor {2}:{3}".format(ip, mask, monitor_ip, monitor_port)

#get external ip
sensor_parent_ip = restclient.GET("http://{0}:{1}/sensors/{2}".format(monitor_ip, monitor_port, sensor_id), async=False)
gateway_external = "external_root"
if sensor_parent_ip != "":
    gateway_external = restclient.GET(sensor_parent_ip, async=False)
"""
#scan sensor network information
getNetworkCMD = "ip addr"
cmd = os.popen(getNetworkCMD)
networkPattern = re.compile(r"\d+:\sen.*")
network = "0.0.0.0"
netmask = "255.255.255.255"
lines = cmd.readlines()
for i in range(0, len(lines)):
    #print line
    if networkPattern.match(lines[i]):
        for j in range(i + 1, len(lines)):
            ipv4Pattern = re.compile(r"\s*inet\s\d+\.\d+\.\d+\.\d+/\d+.*")
            test = lines[j]
            if ipv4Pattern.match(lines[j]):
                splits = lines[j].split()
                ipAndMask = splits[1].split("/")
                network = ipAndMask[0]
                netmask = ipAndMask[1]
                break
        break
"""
#scan local network hosts
hosts = []
getHostsCMD = "nmap -T4 -F {0}/{1}".format(ip, mask)
cmd = os.popen(getHostsCMD)
lines = cmd.readlines()
hostInfoIndexes = []
scanner = nmap.PortScanner()
hosts_report = scanner.scan(hosts = "{0}/{1}".format(ip, mask), arguments="-T4 -F")

scan_report = hosts_report["scan"]
print "scanned {0} hosts".format(len(scan_report.keys()))
print scan_report.keys()

for host_ip in scan_report.keys():
    host = dict()
    data = scan_report[host_ip]
    host["inner_interface"] = data["addresses"]["ipv4"]
    if host["inner_interface"] == gateway:
        host["outer_interface"] = gateway_external
    else:
        host["outer_interface"] = ""
    if "addresses" in data.keys() and "mac" in data["addresses"].keys():
        host["mac"] = data["addresses"]["mac"]
    host["gateway"] = gateway
    services = []

    if "tcp" in data.keys():
        for port_num in data["tcp"].keys():
            service = dict()
            service["port"] = port_num
            service["protocol"] = "tcp"
            service["name"] = data["tcp"][port_num]["name"]
            service["status"] = data["tcp"][port_num]["state"]
            services.append(service)
    host["services"] = services
    hosts.append(host)

restclient.POST("http://{0}:{1}/sensors/{2}/hosts".format(monitor_ip, monitor_port, sensor_id), params=hosts, headers={'Content-Type': 'application/json'}, async=False)
print "send post request {0}".format("http://{0}:{1}/sensors/{2}/hosts".format(monitor_ip, monitor_port, sensor_id))
params_json = json.dumps(hosts)
print "json is:\n {0}".format(params_json)

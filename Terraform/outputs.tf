output "git project" { value = "server1 ip: ${openstack_networking_floatingip_v2.floating_ip_1.address}" }
output "Debian" { value = "server2 ip: ${openstack_networking_floatingip_v2.floating_ip_2.address}" }

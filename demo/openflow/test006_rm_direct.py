
import os, sys, time

if os.getenv('TWISTER_PATH'):
    sys.path.append(os.getenv('TWISTER_PATH') + '/.twister_cache/ce_libs/')

#

def openflow_test():
    '''
    <title>OpenFlow: 008</title>
    <description>Testing Flow Pusher.
    Remove short path from floodlight controller.
    </description>
    '''

    from LibOpenFlow import log_debug, show_switches, FloodLiteControl, StaticFlowPusher, initial_flow_path

    log_debug('\n=== Starting openflow controller test 8 ===')
    log_debug('Descr: Remove short path from floodlight controller.')

    restapi = FloodLiteControl('10.9.6.220', 8080)
    flowpusher = StaticFlowPusher('10.9.6.220')
    fl_switches = restapi.get_switches()

    for s in fl_switches:
       print 'DPID: %s' % s['dpid']

    # ----------------------------------------------------------------------------------------------
    # Removing settings for DIRECT DATAPATH
    # ----------------------------------------------------------------------------------------------
    fl_nr = 0
    tm_wait = 30
    fl_list = []

    for ifp in initial_flow_path:
        fl_nr += 1
        if ifp[2]:
            fl_dict={'switch':ifp[0], 'name':'flow-mod-%i' % fl_nr, 'cookie':'0', 'priority':'32768',
                'ingress-port':str(ifp[1]), 'active':'true', 'actions':'output=%i' % ifp[2]}
        else:
            fl_dict={'switch':ifp[0], 'name':'disable-%s' % str(ifp[1]), 'cookie':'0', 'priority':'32768',
                'ingress-port':str(ifp[1]), 'active':'true', 'actions':''}
        fl_list.append(fl_dict)

    log_debug('\nRemoving short datapath settings...\n')

    for fl in fl_list:
        flowpusher.remove(fl)
        time.sleep(1)
        log_debug('Flow removed:\n %s' % str(fl))

    show_switches()

    return 'PASS'

#

_RESULT = openflow_test()

#
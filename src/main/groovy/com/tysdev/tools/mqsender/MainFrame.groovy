package com.tysdev.tools.mqsender

import com.tysdev.tools.mqsender.jmsproviders.JmsProvider
import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*
import java.text.ParseException


/**
 * Application's main frame
 *
 * @author Tyryshkin Alexander (C)
 */

//Frame size:
Integer FRAME_WIDTH = 500;
Integer FRAME_HEIGHT = 550;
String FRAME_TITLE = 'JMS Message sender';


SwingBuilder.build
        {
            frame(
                    title: FRAME_TITLE,
                    defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                    show: true,
                    windowOpened: {
                        radioActiveMq.selected = true
                        txtQManager.visible = false
                        txtChannel.visible = false
                    }
            )
                    {
                        borderLayout()

                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                        //  Components
                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                        panel(constraints: BorderLayout.NORTH)
                                {
                                    borderLayout()

                                    textField(
                                            id: "txtHost",
                                            text: 'localhost',
                                            columns: 20,
                                            constraints: BorderLayout.NORTH,
                                            toolTipText: "JMS broker host address"
                                    )
                                    textField(
                                            id: "txtPort",
                                            text: '61616',
                                            columns: 20,
                                            constraints: BorderLayout.WEST,
                                            toolTipText: "JMS broker port"
                                    )
                                    textField(
                                            id: "txtQManager",
                                            text: 'QM_TEST',
                                            constraints: BorderLayout.CENTER,
                                            toolTipText: "Queue manager"
                                    )
                                    textField(
                                            id: "txtChannel",
                                            text: 'SYSTEM.DEF.SVRCONN',
                                            constraints: BorderLayout.EAST,
                                            toolTipText: "Channel"
                                    )
                                    panel(constraints: BorderLayout.SOUTH)
                                            {
                                                borderLayout()

                                                radioButton(
                                                        id: "radioActiveMq",
                                                        label: "ActiveMQ",
                                                        constraints: BorderLayout.WEST,
                                                        actionPerformed: {
                                                            radioWebSphereMq.selected = false
                                                            txtQManager.visible = false
                                                            txtChannel.visible = false
                                                        }
                                                )

                                                radioButton(
                                                        id: "radioWebSphereMq",
                                                        label: "WebSphere MQ",
                                                        constraints: BorderLayout.EAST,
                                                        actionPerformed: {
                                                            radioActiveMq.selected = false
                                                            txtQManager.visible = true
                                                            txtChannel.visible = true
                                                        }
                                                )
                                            }
                                }

                        panel(constraints: BorderLayout.CENTER)
                                {
                                    borderLayout()

                                    textField(
                                            id: "txtQueue",
                                            text: 'Queue...',
                                            constraints: BorderLayout.NORTH
                                    )

                                    scrollPane(constraints: BorderLayout.CENTER)
                                            {
                                                textArea(
                                                        id: "txtMessage",
                                                        text: 'Message...'
                                                )
                                            }
                                }

                        panel(constraints: BorderLayout.SOUTH)
                                {
                                    borderLayout()

                                    label(
                                            id: "repeatLabel",
                                            text: "Send times:",
                                            constraints: BorderLayout.WEST
                                    )
                                    textField(
                                            id: "txtRepeat",
                                            text: '1',
                                            constraints: BorderLayout.CENTER
                                    )
                                    button(
                                            id: 'buttonSend',
                                            label: 'SEND',
                                            constraints: BorderLayout.EAST,
                                            actionPerformed: {
                                                try {
                                                    JmsProvider jmsProvider;

                                                    if (radioActiveMq.selected) {
                                                        jmsProvider = JmsProviderFactory.getJmsProvider(JmsProviderFactory.JMS_PROVIDER_ACTIVEMQ)
                                                        jmsProvider.connect(txtHost.text, txtPort.text, null, null)
                                                    } else {
                                                        jmsProvider = JmsProviderFactory.getJmsProvider(JmsProviderFactory.JMS_PROVIDER_WEBSPHEREMQ)
                                                        jmsProvider.connect(txtHost.text, txtPort.text, txtQManager.text, txtChannel.text)
                                                    }

                                                    int repeat = 1
                                                    try {
                                                        repeat = Integer.parseInt(txtRepeat.text)
                                                        if (repeat <= 0) {
                                                            repeat = 1
                                                        }
                                                    }
                                                    catch (ParseException error) {
                                                        repeat = 1;
                                                    }
                                                    txtRepeat.text = repeat.toString()

                                                    for (int i = 0; i < repeat; i++) {
                                                        jmsProvider.sendTextMessage(txtQueue.text, txtMessage.text)
                                                        currentStatus.text = "SENT!  " + (new Date()).toString()
                                                    }
                                                }
                                                catch (Exception error) {
                                                    currentStatus.text = "ERROR! " + error.getMessage()
                                                    error.printStackTrace()
                                                }
                                            }
                                    )
                                    label(
                                            id: "currentStatus",
                                            text: "Ready.",
                                            constraints: BorderLayout.SOUTH
                                    )
                                }

                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                        //  MENU
                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                        menuBar()
                                {
                                    menu(text: 'Program')
                                            {
                                                menuItem(text: 'About', actionPerformed: {
                                                    optionPane().showMessageDialog(null,
                                                            FRAME_TITLE + "\n\nTyryshkin A.S. (С) 2013\n",
                                                            "About",
                                                            JOptionPane.INFORMATION_MESSAGE)
                                                })
                                                separator(id: 'menuSep')
                                                menuItem(text: 'Exit', actionPerformed: { dispose() })
                                            }
                                }

                        // Setting form position at center of the screen:
                        current.pack()
                        Point cp = GraphicsEnvironment.localGraphicsEnvironment.centerPoint
                        current.location = new Point((int) (cp.x - FRAME_WIDTH / 2), (int) (cp.y - FRAME_HEIGHT / 2))
                        current.size = [FRAME_WIDTH, FRAME_HEIGHT]
                    }
        }

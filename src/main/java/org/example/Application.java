package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Application {
    public static void main(String[] args){
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        Logger logger = Logger.getLogger("Logger");
        logger.setLevel(Level.INFO);
        FindElementManager findElementManager = new FindElementManager(List.of(
                new FindElement(new InfoWeb("Bpm " + "Power", "CPU", "https://www.bpm-power.com/it/online/componenti-pc/processori/cpu-intel-core-i9" +
                "-b2026879", new ByParameters(List.of("prezzoScheda"), new ArrayList<>(), new ArrayList<>()), 594.69,
                 "recursive")),
                new FindElement(new InfoWeb("Amazon", "RAM", "https://www.amazon.it/Kingston-2x16GB-6000MT-Memoria" +
                        "-Computer/dp/B0BD5XBFS6/ref=sr_1_3?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&keywords=Kingston%2BFURY%2BBeast%2BRGB%2B32%2BGB%2B(2%2Bx%2B16%2BGB)%2BDDR5-6000%2BCL36%2BMemory&qid=1696083935&s=pc&sr=1-3&ufe=app_do%3Aamzn1.fos.9d4f9b77-768c-4a4e-94ad-33674c20ab35&th=1",
                        new ByParameters(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), 142.82,
                        "manual"))));

        SendMessageManager sendMessageManager = new SendMessageManager();
        sendMessageManager.setMessage("Pronto per rivere un comando");
        GetMessageManager getMessageManager = new GetMessageManager();
        String latestMessage = "No recent message";

        try {
            sendMessageManager.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("In attesa di un comando");

        while (true) {
            try {
                latestMessage = getMessageManager.getLatestMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (latestMessage.equals("Start")) {
                logger.info("Ricevuto comando Start");
                while (true) {
                    try {
                        latestMessage = getMessageManager.getLatestMessage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (latestMessage.equals("Stop") || latestMessage.equals("Shutdown")) {
                        if (latestMessage.equals("Stop")) {
                            logger.info("Ricevuto comando Stop");
                        } else {
                            logger.info("Ricevuto comando Shutdown");
                        }
                        break;
                    }

                    try {
                        findElementManager.refreshWegPages();
                    } catch (IllegalArgumentException e) {
                        logger.warning("Qualcosa è andato storto nel refresh");
                    }

                    findElementManager.check_print_Values(sendMessageManager);

                    try {
                        Thread.sleep(10000);//4800000
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (latestMessage.equals("Shutdown")) {
                break;
            }
        }
        findElementManager.closehWegPages();
        sendMessageManager.setMessage("Programma terminato con successo");
        try {
            sendMessageManager.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Programma terminato con successo");
    }
}
package org.example;

import java.io.IOException;
import java.util.List;

public class FindElementManager {
    private List<FindElement> list;

    public FindElementManager(List<FindElement> list) {
        this.list = list;
    }

    public void check_print_Values(SendMessageManager sendMessageManager) {
        sendMessageManager.setMessage("--------------------");
        try {
            sendMessageManager.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        list.forEach(l -> {
            double actualPrice = polishValue(l.getElementValue());
            if (actualPrice > l.getRef_Price()) {
                sendMessageManager.setMessage("Il prezzo del prodotto \"" + l.getProdName() + "\" del sito \"" + l.getWebName() + "\" e' AUMENTATO di" + " " + Math.round((actualPrice - l.getRef_Price())*100)/100.0 + " (Prezzo di riferimento = " + l.getRef_Price() + ")");
            } else if (actualPrice == l.getRef_Price()) {
                sendMessageManager.setMessage("Il prezzo del prodotto \"" + l.getProdName() + "\" del sito \"" + l.getWebName() + "\" NON E' CAMBIATO" + " (Prezzo di riferimento = " + l.getRef_Price() + ")");
            } else {
                sendMessageManager.setMessage("Il prezzo del prodotto \"" + l.getProdName() + "\" del sito \"" + l.getWebName() + "\" e' SCESO di " + Math.round((l.getRef_Price() - actualPrice)*100)/100.0 + " (Prezzo di riferimento = " + l.getRef_Price() + ")");
            }
            try {
                sendMessageManager.sendMessage();
            } catch (IOException e) {
                throw new RuntimeException(e); //todo
            }
        });
        sendMessageManager.setMessage("--------------------");
        try {
            sendMessageManager.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshWegPages() {
        list.forEach(FindElement::refreshWebPage);
    }

    public void closehWegPages() {
        list.forEach(FindElement::closeWebPage);
    }

    public List<FindElement> getList() {
        return list;
    }

    public void setList(List<FindElement> list) {
        this.list = list;
    }

    private static double polishValue(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); ++i) {
            if (Character.isDigit(value.codePointAt(i))) {
                builder.append(value.charAt(i));
                if ((i + 2) < value.length() && value.charAt(i + 1) == ',' && Character.isDigit(value.codePointAt(i + 2))) {
                    builder.append(".").append(value.charAt(i + 2));
                    ++i;
                    ++i;
                }
            }
        }
        return Double.parseDouble(builder.toString());
    }
}

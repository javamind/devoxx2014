// $Id$
// Copyright (c) 2011 Boiron - Tous droits réservés.

package com.ninjamind.conference.utils;

import com.boiron.framework.commons.DateUtil;
import com.boiron.framework.commons.StringUtil;
import com.google.common.base.Strings;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Factory de logger.
 *
 * @author pernin_v
 * @see "http://www.javaspecialists.co.za/archive/newsletter.do?issue=137"
 * @since 0.1
 */
public final class LoggerFactory {
    /**
     * Nombre total de caractere max des erreurs sauvegardees en base
     */
    private static final int MAX_LENGTH_ERROR = 4000;

    private LoggerFactory() {
    }

    /**
     * Renvoie un logger correspondant à la classe appelante
     *
     * @return Logger
     */
    public static Logger make() {

        Throwable t = new Throwable();  //NOPMD Récupération du nom de la classe courante génériquement
        StackTraceElement directCaller = t.getStackTrace()[1];
        return Logger.getLogger(directCaller.getClassName());
    }

    /**
     * Cette méthode renvoie la liste des fichiers associés à un logger
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> getLogsRegistreNomFichier(Logger logger) {
        Enumeration<Appender> enumLog = logger.getAllAppenders();
        //Si on ne trouve pas d'appender pour notre logger on regarde les parents sauf si le parent est Root
        if (!enumLog.hasMoreElements() && logger.getParent() != null && logger.getParent() instanceof Logger
                && !"root".equals(logger.getParent().getName())) {
            return getLogsRegistreNomFichier((Logger) logger.getParent());
        }
        List<String> listLog = new ArrayList<String>();
        while (enumLog.hasMoreElements()) {
            Appender appender = enumLog.nextElement();
            if (appender instanceof FileAppender) {
                listLog.add(((FileAppender) appender).getFile());
            }
        }
        return listLog;
    }

    /**
     * Cette méthode permet de déterminer le fichier de log lié à un logger et une date
     *
     * @param logger
     * @param date
     * @return
     */
    public static File getLoggerFile(Logger logger, Date date) {

        List<String> loggers = LoggerFactory.getLogsRegistreNomFichier(logger);
        //On s'interesse au dernier logger en sachant que nos loggers ont cette forme
        //xxx-yyyyyyyyyyy2013-04-03.log et contiennent tous la date à l'intérieur. De plus
        //la politique de rotation des logs se fait 00:00
        if (CollectionUtils.isNotEmpty(loggers)) {
            String filename = loggers.get(0);
            //On decoupe le nom pour ne garder que la partie sans date
            String prefixe = filename.substring(0, filename.length() - 14);
            int[] composantesDate = DateUtil.getComposantesDate(date);
            //Le fichier de log qui nous interesse est donc
            String logFileName = String.format("%s%d-%s-%s.log", prefixe, composantesDate[0],
                    Strings.padStart(String.valueOf(composantesDate[1]), 2, '0'),
                    Strings.padStart(String.valueOf(composantesDate[2]), 2, '0'));
            return new File(logFileName);
        }

        return null;
    }

    public static String getErrorForSynHisto(Throwable t) {
        //Si l'exception n'est pas renseignée on retourne null
        if (t == null) {
            return null;
        }
        StringBuffer messageComplet = new StringBuffer();
        messageComplet.append("ERREUR : ").append(t.getLocalizedMessage()).append(StringUtil.CRLF());
        StackTraceElement[] stack = t.getStackTrace();
        if (stack != null && stack.length > 0) {
            messageComplet.append("*** ").append(stack[0]).append(StringUtil.CRLF());
        }
        t = t.getCause();
        while (t != null) {
            messageComplet.append("ERREUR PARENTE : ").append(t.getLocalizedMessage()).append(StringUtil.CRLF());
            stack = t.getStackTrace();
            if (stack != null && stack.length > 0) {
                messageComplet.append("*** ").append(stack[0]).append(StringUtil.CRLF());
            }
            t = t.getCause();
        }
        return StringUtils.left(messageComplet.toString(), MAX_LENGTH_ERROR);
    }
}
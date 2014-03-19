devoxx2014
==========

Projet lié à la présentation <a href="http://cfp.devoxx.fr/devoxxfr2014/talk/BBV-277/Le%20bon%20testeur%20il%20teste....%20et%20le%20mauvais%20testeur%20il%20teste%20aussi...">Le bon testeur il teste, le mauvais testeur il teste..."</a>
<br>
Dans le cadre de notre intervention (Agnes Crepet et Guillaume EHRET) à Devoxx 2014, nous avons créé un projet exemple. Ce projet ce décline en 2 modules : une partie serveur fournissant une API restfull et une partie cliente Angular JS. Ce repository est dédié à la partie serveur.
<br>
Cette petite application simule une gestion des conférences de développeurs se déroulant en France
et dans le monde. Le modèle de données est le suivant
<ul>
<li>conférence</li>
<li>talk : une conférence à x talks. Un talk a x talks</li>
<li>speaker : une conférence à x speakers. Un speaker a x speakers</li>
<li>pays : une conférence est liée à un pays, un speaker est lié à un pays</li>
</ul>

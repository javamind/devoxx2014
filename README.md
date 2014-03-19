devoxx2014
==========

<p>
Projet lié à la présentation <a href="http://cfp.devoxx.fr/devoxxfr2014/talk/BBV-277/Le%20bon%20testeur%20il%20teste....%20et%20le%20mauvais%20testeur%20il%20teste%20aussi...">Le bon testeur il teste, le mauvais testeur il teste..."</a>
</p>
<p>
Dans le cadre de notre intervention (Agnes Crepet et Guillaume EHRET) à Devoxx 2014, nous avons créé un projet exemple. Ce projet ce décline en 2 modules : une partie serveur fournissant une API restfull et une partie cliente Angular JS. Ce repository est dédié à la partie serveur.
</p>
<p>
Cette petite application simule une gestion des conférences de développeurs se déroulant en France
et dans le monde. Le modèle de données est le suivant
<ul>
<li>conférence</li>
<li>talk : une conférence à x talks. Un talk a x talks</li>
<li>speaker : une conférence à x speakers. Un speaker a x speakers</li>
<li>pays : une conférence est liée à un pays, un speaker est lié à un pays</li>
</ul>
</p>

Au niveau de la stack technique nous utilisons
<ul>
<li>postgresql 9.1</li>
<li>spring core, test et webmvc : 4.0.2.RELEASE</li>
<li>spring data jpa : 1.5.0.RELEASE</li>
<li>hibernate 4.3.1.Final</li>
<li>dbsetup 1.2.0 (en parallèle de dbunit 2.4.9)</li>
<li>fest-assert 1.4</li>
<li>javax.servlet-api : 3.0.1</li>
</ul>

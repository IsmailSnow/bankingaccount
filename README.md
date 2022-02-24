Créer une lib permettant la gestion de comptes bancaires géré en euros (seulement):

Un compte bancaire est défini par les informations suivantes:
Un identifiant de compte
Les noms, prénoms, date de naissance, adresse mail et numéro de téléphone du client titulaire

La balance (ç-à-d le montant disponible sur le compte)
la liste des opérations (crédit ou débit)
un caractère (statut) actif/bloqué
Une opération est définie par:
Sa date et son heure,
Sa nature (débit/crédit)
Le montant concerné (la devise - disons l'euro - est constante)

La librairie à développer permettra de :
Créer un nouveau compte (supposé actif et avec une balance de 0€). Toutes les informations doivent être initialisées,
Consulter le compte, par le renvoi de son image JSON (devra retourner tous les champs. La liste des opérations se limitera aux 10 dernières (celles qui existent s'il n'en existe pas 10 - par ordre chronologique). 
Procéder à une opération de crédit,
Procéder à une opération de débit
Bloquer /débloquer un compte.
Créer un virement d'un compte à l'autre,

Règles importantes :
Il est impossible de créditer ou débiter un compte bloqué
Un compte ne peut pas être "dans le rouge" (avoir une balance négative).

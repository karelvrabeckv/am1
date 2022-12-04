# DÚ 6

## Proces

* Uživatel podá žádost o smazání příslušného záznamu (DELETE /tour/{tid} bez autorizace)
* Server zpětně pošle link, kde uživatel může svou žádost sledovat (GET /deletion/{did})
* Jakmile si žádost přečte administrátor, pak definivně smaže příslušný záznam (DELETE /tour/{tid} s autorizací)

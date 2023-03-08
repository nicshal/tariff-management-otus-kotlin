package ru.nicshal.tariff.management.mappers.exceptions

import ru.nicshal.tariff.management.common.models.TrmngCommand

class UnknownTrmngCommand(command: TrmngCommand) : Throwable("Wrong command $command at mapping toTransport stage")

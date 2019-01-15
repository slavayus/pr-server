package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

abstract class DictionaryCommand {
    final DictionaryRepository repository;

    DictionaryCommand(DictionaryRepository repository) {
        this.repository = repository;
    }

    abstract void execute(Dictionary dictionary);
}

package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

public class FindCommand extends DictionaryCommand {
    public FindCommand(DictionaryRepository repository) {
        super(repository);
    }

    @Override
    public void execute(Dictionary dictionary) {
        repository.find(dictionary);
    }
}

__config() -> {
    'scope' -> 'global',
    'resources' -> [
        {
            'source' -> '/configs/spawn_conditions.json',
            'target' -> 'spawn_conditions.json',
            'shared' -> true,
        },
    ],
};

config_data = read_file('entity_blacklist','shared_json');
print(config_data)

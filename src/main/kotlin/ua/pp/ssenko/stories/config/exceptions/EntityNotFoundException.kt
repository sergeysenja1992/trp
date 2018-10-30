package ua.pp.ssenko.stories.config.exceptions

/**
 * Custom esception for Not found entity by ID.
 * Created by medved on 21.06.17.
 */
class EntityNotFoundException(message: String) : RuntimeException(message)

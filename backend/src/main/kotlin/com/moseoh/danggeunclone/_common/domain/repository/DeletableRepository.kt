package com.moseoh.danggeunclone._common.domain.repository

import com.moseoh.danggeunclone._common.domain.ModifiableEntity
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface DeletableRepository<T : ModifiableEntity> : ModifiableRepository<T>

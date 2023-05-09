package com.nhathm.wellcare.base

import android.view.LayoutInflater
import android.view.ViewGroup

typealias InflateAc<T> = (LayoutInflater) -> T

typealias InflateFr<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
package com.example.englishe4.common

class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
}

class Trie {
    private val root = TrieNode()
    private var cachedPrefix: String? = null
    private var cachedResults: List<String> = emptyList()

    fun insert(word: String) {
        var current = root
        for (char in word) {
            current = current.children.computeIfAbsent(char) { TrieNode() }
        }
        current.isEndOfWord = true
    }

    fun search(prefix: String): List<String> {
        if (cachedPrefix != null && prefix.startsWith(cachedPrefix!!)) {
            val newResults = mutableListOf<String>()
            for (result in cachedResults) {
                if (result.startsWith(prefix, ignoreCase = true)) {
                    newResults.add(result)
                }
            }
            updateCache(prefix, newResults)
            return newResults
        } else {
            clearCache()
            return searchFromRoot(prefix)
        }
    }


    private fun findWordsWithPrefix(prefix: String, node: TrieNode): List<String> {
        val results = mutableListOf<String>()
        if (node.isEndOfWord) {
            results.add(prefix)
        }
        for ((char, childNode) in node.children) {
            results.addAll(findWordsWithPrefix(prefix + char, childNode))
        }
        return results
    }

    private fun searchFromRoot(prefix: String): List<String> {
        if (prefix.isEmpty()) {
            return emptyList()
        }
        var current = root
        prefix.forEach { char ->
            current = current.children[char] ?: return emptyList()
        }
        return findWordsWithPrefix(prefix, current)
    }

    private fun updateCache(prefix: String, results: List<String>) {
        if (cachedPrefix != prefix || cachedResults != results) {
            cachedPrefix = prefix
            cachedResults = results.toList()
        }
    }

    private fun clearCache() {
        cachedPrefix = null
        cachedResults = emptyList()
    }
}
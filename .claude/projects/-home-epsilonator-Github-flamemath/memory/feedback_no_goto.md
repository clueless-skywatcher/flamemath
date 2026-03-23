---
name: no_labeled_breaks
description: User does not want labeled breaks/continues (considers them goto statements) — use boolean flags instead
type: feedback
---

Do not suggest labeled `break` or `continue` statements in Java. The user considers these equivalent to goto statements.

**Why:** Strong personal style preference — user explicitly rejected the suggestion multiple times.

**How to apply:** When nested loop control flow is needed, use boolean flags or extract to helper methods instead.

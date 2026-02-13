# Junie Configuration

This directory contains guidelines and configuration for Junie, the AI development assistant for the Effective Office project.

## Files

- **[guidelines.md](./guidelines.md)** - Complete project guidelines for Junie including:
  - Project structure and overview
  - Development conventions and best practices
  - Testing guidelines
  - Architecture patterns (Clean Architecture, SOLID, DRY, KISS)
  - Client application development (KMP, Compose, Navigation)
  - Gradle convention plugins
  - **Memory Bank integration** - How to use and maintain project documentation
- **[memory-bank-checklist.md](./memory-bank-checklist.md)** - Quick reference checklist for Memory Bank workflow

## Integration with Memory Bank

Junie is configured to work closely with the [Memory Bank](../.memory-bank/index.md) - a living documentation system that maintains up-to-date information about:

- Project architecture and design decisions
- Technology stack and versions
- Development and testing conventions
- Active tasks and features

### Workflow

```
┌──────────────┐
│    User      │
│   Request    │
└──────┬───────┘
       │
       v
┌──────────────────────┐
│  Junie consults      │◄────┐
│  Memory Bank first   │     │
└──────┬───────────────┘     │
       │                     │
       v                     │
┌──────────────────────┐     │
│  Implements feature  │     │
│  following patterns  │     │
└──────┬───────────────┘     │
       │                     │
       v                     │
┌──────────────────────┐     │
│  Suggests Memory     │     │
│  Bank update if      │─────┘
│  architecture changed│
└──────────────────────┘
```

### Key Principles

1. **Check Memory Bank first** - Always consult relevant documentation before implementing features
2. **Follow documented conventions** - Adhere to patterns and practices in Memory Bank
3. **Suggest updates when needed** - Propose Memory Bank updates for significant changes (importance ≥ 2)
4. **Keep it current** - Help maintain Memory Bank as the source of truth

## See Also

- [Memory Bank Index](../.memory-bank/index.md) - Main documentation hub
- [Memory Bank Steward Rules](../.cursor/rules/memory-bank.mdc) - Update policy and format

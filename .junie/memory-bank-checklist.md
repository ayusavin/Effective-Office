# Quick Memory Bank Checklist for Junie

## Before Every Task

- [ ] Read relevant sections from `.memory-bank/`
- [ ] Check if similar work exists or is documented
- [ ] Verify tech stack versions
- [ ] Review conventions for the area you're working in

## During Implementation

- [ ] Follow architecture patterns from Memory Bank
- [ ] Use coding conventions from `development-conventions.md`
- [ ] Apply testing practices from `testing-conventions.md`
- [ ] Match existing module structure and organization

## After Implementation

Ask yourself:
- Did I create a new module or change module interactions? → **Suggest update**
- Did I introduce/change a public API or contract? → **Suggest update**
- Did I establish a new convention or pattern? → **Suggest update**
- Did I update tech stack (framework, version)? → **Suggest update**
- Did I modify core business entities or workflows? → **Suggest update**
- Was this just an internal refactor or bug fix? → **No update needed**

## Memory Bank Locations

| What to Find | Where to Look |
|--------------|---------------|
| App features & overview | `.memory-bank/product-overview/apps.md` |
| Frameworks & versions | `.memory-bank/tech-details/tech-stack.md` |
| Module structure & patterns | `.memory-bank/tech-details/architecture.md` |
| Git, code style, conventions | `.memory-bank/steerings/development-conventions.md` |
| Testing strategy & tools | `.memory-bank/steerings/testing-conventions.md` |
| Current work & priorities | `.memory-bank/tasks/` |

## Update Importance Scale

| Level | Type | Example | Action |
|-------|------|---------|--------|
| 3 | Major | New module, API, architecture change | **Always suggest** |
| 2 | Moderate | New convention, interface, contract | **Always suggest** |
| 1 | Minor | Internal helper refactor | Skip |
| 0 | Noise | Temporary experiment | Skip |

## Quick Update Template

```
MEMORY_SUGGESTION
version: 1
importance: 2
summary: "Add new booking API endpoint"
rationale: "Public API change needs documentation"
changes:
  - path: ".memory-bank/tech-details/architecture.md"
    op: "append"
    section: "Backend Architecture"
    content_format: "md"
    content: |-
      ### Booking API
      - POST /api/bookings - Create new booking
validation:
  conflicts_checked: true
  secrets_present: false
END
```

## Golden Rules

1. ✅ **Always check Memory Bank first** before starting work
2. ✅ **Only suggest updates for lasting, significant changes** (importance ≥ 2)
3. ✅ **Keep updates concise and factual** - no implementation details
4. ❌ **Never include secrets** in Memory Bank updates
5. ❌ **Don't document temporary code** or experiments

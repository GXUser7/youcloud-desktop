---
name: Sonic Expressive
colors:
  surface: '#141315'
  surface-dim: '#141315'
  surface-bright: '#3a393b'
  surface-container-lowest: '#0f0e10'
  surface-container-low: '#1c1b1d'
  surface-container: '#201f21'
  surface-container-high: '#2b292c'
  surface-container-highest: '#363436'
  on-surface: '#e6e1e4'
  on-surface-variant: '#cac4d7'
  inverse-surface: '#e6e1e4'
  inverse-on-surface: '#313032'
  outline: '#938ea0'
  outline-variant: '#484554'
  surface-tint: '#cbbeff'
  primary: '#cbbeff'
  on-primary: '#340098'
  primary-container: '#6442d6'
  on-primary-container: '#ded4ff'
  inverse-primary: '#6341d5'
  secondary: '#ccbeff'
  on-secondary: '#340c8f'
  secondary-container: '#4b2ea6'
  on-secondary-container: '#bba9ff'
  tertiary: '#cac5c7'
  on-tertiary: '#323031'
  tertiary-container: '#615e60'
  on-tertiary-container: '#ded8da'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#e7deff'
  primary-fixed-dim: '#cbbeff'
  on-primary-fixed: '#1e0061'
  on-primary-fixed-variant: '#4b21bd'
  secondary-fixed: '#e7deff'
  secondary-fixed-dim: '#ccbeff'
  on-secondary-fixed: '#1e0060'
  on-secondary-fixed-variant: '#4b2ea6'
  tertiary-fixed: '#e7e1e3'
  tertiary-fixed-dim: '#cac5c7'
  on-tertiary-fixed: '#1d1b1d'
  on-tertiary-fixed-variant: '#494648'
  background: '#141315'
  on-background: '#e6e1e4'
  surface-variant: '#2D2A2D'
  brand-accent: '#D64264'
  on-surface-vibrant: '#FEFBFF'
typography:
  display-lg:
    fontFamily: plusJakartaSans
    fontSize: 57px
    fontWeight: '800'
    lineHeight: 64px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: plusJakartaSans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: plusJakartaSans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
  title-lg:
    fontFamily: plusJakartaSans
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
  body-lg:
    fontFamily: inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: jetbrainsMono
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  margin-mobile: 16px
  margin-desktop: 24px
  gutter: 16px
  nav-rail-width: 80px
  nav-drawer-width: 280px
  player-height: 96px
---

## Brand & Style
The design system is a high-energy, immersive framework tailored for a premium music streaming experience. It leverages the **Material 3 Expressive** design movement, characterized by oversized headlines, generous roundedness, and a "dynamic" color logic that responds to the content (album art). 

The aesthetic is **Modern/Expressive**: it balances the systematic reliability of Material Design with the bold, emotional vibrancy of a creative media platform. The target audience is music enthusiasts who value high-fidelity visuals and tactile, fluid interactions. The UI should feel alive, rhythmic, and deeply focused on the art, using depth and scale to create a clear hierarchy.

## Colors
The system utilizes a default **Dark Mode** foundation to let album artwork and media content take center stage. The palette is built on Material 3 Tonal Palettes:

- **Primary:** A deep, resonant violet (#6442D6) used for key actions and branding.
- **Secondary:** A vibrant lavender (#9F86FF) used for highlighting and active states.
- **Tertiary/Accents:** A high-contrast "vibrant red/pink" (#D64264) is reserved for expressive moments like "Like" buttons or live indicators.
- **Neutral:** A deep carbon black (#1C1B1D) for the background, ensuring maximum contrast with the vibrant accents.

In implementation, the secondary and tertiary containers should ideally derive their hue from the current track's album art using M3's dynamic color algorithms, ensuring the UI "vibes" with the music.

## Typography
This design system employs a three-tier font strategy to align with the M3 Expressive style:

- **Display & Headings:** Uses *Plus Jakarta Sans*. It is bold and geometric, providing the "Expressive" character needed for artist names and playlist titles.
- **Body & Content:** Uses *Inter*. This ensures maximum legibility in dense lists, track descriptions, and settings menus.
- **Labels & Metadata:** Uses *JetBrains Mono*. A technical, monospaced touch for timestamps, bitrates, and track numbers, reinforcing the "player" utility.

Scaling follows the M3 scale. For mobile, headline sizes are slightly reduced to maintain optical balance, while display sizes are reserved for large-format tablet and desktop hero sections.

## Layout & Spacing
The layout is optimized for a **Fixed/Fluid Hybrid** grid system:

- **Desktop Structure:** Features a persistent **Navigation Rail** (80px) that can expand into a **Navigation Drawer** (280px) for library management. The main content area uses a fluid 12-column grid.
- **Persistent Player:** A bottom-docked player (96px height) remains visible at all times, acting as the anchor of the experience.
- **Spacing Rhythm:** Based on an 8px base unit. Component internal padding should be generous (16px - 24px) to support the "Expressive" oversized feel.
- **Responsive Behavior:** On tablet, the Drawer collapses to a Rail. On mobile, the Rail moves to a Bottom Navigation bar, and the Player becomes a floating "mini-player" or a condensed bar above the navigation.

## Elevation & Depth
Elevation in this design system is conveyed through **Tonal Layers** and **Tinted Shadows**, moving away from pure greyscale shadows.

- **Level 0 (Surface):** The base background (#1C1B1D).
- **Level 1 (Cards/Rail):** A slightly lighter surface (#2D2A2D) with a subtle 2dp elevation.
- **Level 2 (Active States/Modals):** Surfaces are tinted with the primary color at 5-8% opacity to create a sense of glowing depth.
- **Glassmorphism:** The persistent bottom player uses a 20px backdrop blur with a semi-transparent dark tint (80% opacity) to allow the scrolling content to peek through, creating a sense of layered space.

## Shapes
In accordance with Material 3 Expressive, the shape language is aggressively rounded to feel friendly and tactile.

- **Cards (Playlists/Mixes):** Use `rounded-xl` (1.5rem / 24px) to emphasize the container.
- **Buttons:** Use `rounded-md` (0.5rem / 8px) for standard actions, while "Play" and "Shuffle" buttons use a full **Pill** shape for high visibility.
- **Inputs:** Use `rounded-lg` (1rem / 16px) for a modern, soft appearance.
- **Thumbnails:** Album art should always have a 12px corner radius to avoid the "sharpness" of traditional grids.

## Components

- **Buttons:** Primary buttons are high-contrast violet. "Expressive" icon buttons for playback (Play/Pause) are oversized (64px+) and use a Primary container with a "squishy" press state.
- **Cards:** Music mix cards use an "Image-First" layout. The text is placed on a tonal container at the bottom of the card with `rounded-xl` corners. Hovering over a card triggers a subtle scale-up effect (1.05x).
- **Sliders:** The track progress bar and volume sliders are "High-Contrast." The active track is thicker (8px) than the inactive track (4px), with a large, visible thumb (handle) that grows on interaction.
- **Chips:** Used for genre tags or "Following" artists. These use low-contrast outlines in their default state and fill with the Primary color when active.
- **List Items:** Track lists use 72px height for comfort, featuring a trailing "more" vertical icon and a leading thumbnail or track number in *JetBrains Mono*.
- **Navigation Rail:** Vertical orientation on desktop. Active items are highlighted with a pill-shaped "active indicator" behind the icon, as per M3 specs.
# 🎮 GameGridApp - Standard template for boarding game

A feature-rich Java implementation of the classic Sokoban puzzle game with advanced mechanics, mob AI, and custom level design. Design with the motivation of the structure of the code is compatible with other game. For example, you want to create a PACMAN game, change the *.png and rewrite the behavior of `Player.java` and it's done. Finally, the `.jar` contains all the game, no need installer just JRE.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue)
![Status](https://img.shields.io/badge/status-active-success)

---



## 📖 Project Origins

This application originated as a **school project** but evolved into a comprehensive game engine. While the base GUI components were provided, it has been ENTIRELY remastered and, **all game logic, mechanics, and features are original implementations**.

### Development Journey
- **First major Java project** - Learning through implementation
- **Continuous improvements** - From basic mechanics to advanced AI pathfinding
- **Recent enhancements** (2024) - Multi-threaded mob system with A* pathfinding, resizable UI, game reset functionality

---

## 🎯 Game Overview

**Sokoban** is a classic puzzle game where you push crates onto target locations. This implementation extends the traditional gameplay with:

- **Advanced tile types** (risky floors, keys, doors, coins)
- **AI-controlled mobs** using A* pathfinding
- **Resizable game window** with dynamic scaling
- **Multiple levels** with varying difficulty
- **Timer and statistics tracking**
- **Sound effects and background music**

---

## ✨ Key Features

### 🧩 Gameplay Mechanics
- **Classic tiles**: Walls, floors, crates, targets
- **Special tiles**:
  - 🕳️ **Holes** - Instant game over if stepped on
  - ⚠️ **Risky Floors** - Can only be walked on once before becoming a hole
  - 🔑 **Keys** - Collectible items to unlock doors
  - 🚪 **Doors** - Locked barriers requiring keys
  - 🪙 **Coins** - Collectible bonus items
  - 📦 **Shift** - Special crates that can hide items underneath

### 🤖 AI System
- **Mob entities** with autonomous movement
- **A\* pathfinding algorithm** for intelligent chasing behavior
- **Real-time path recalculation** when player moves
- **Multi-threaded execution** for smooth gameplay

### 🎨 User Interface
- **Resizable game window** with automatic scaling
- **Dynamic grid rendering** that adapts to window size
- **Layered panel system** (home, game, win, end screens)
- **Level selection interface** with progress bar
- **Real-time statistics display** (steps, keys, coins, timer)

### 🔄 Game Reset System
- **Complete level reset** functionality
- **Statistics reset** (timer, keys, coins, steps)
- **Map state restoration** to initial configuration
- **Synchronized entity states** (player and mobs)

### 🎵 Audio
- **Background music** system with volume control
- **Mute/unmute** functionality
- **Audio thread management**

---

## 🏗️ Architecture

### Class Hierarchy

```
Entity (abstract)
├── Player - User-controlled character
└── Mob - AI-controlled enemy

Tile (abstract)
├── Plot (walkable terrain)
│   ├── Floor
│   ├── Wall
│   ├── Target
│   └── Hole
└── Item (interactive objects)
    ├── Crate
    ├── Key
    ├── Coin
    ├── Door
    ├── RiskyFloor
    └── Shift
```

### Package Structure

```
be.weber.sokoban.code
├── game/          # Core game logic
│   ├── Entity.java        # Abstract entity base class
│   ├── Player.java        # Player implementation
│   ├── Mob.java          # AI mob implementation
│   ├── Map.java          # Level map management
│   ├── GameInstance.java # Game loop and threading
│   ├── Actions.java      # Movement actions enum
│   ├── Levels.java       # Level definitions
│   └── SoundTrack.java   # Audio management
├── gui/           # GUI components
│   ├── GameGui.java      # Main game display
│   ├── GameGrid.java     # Grid rendering
│   └── SokobanChrono.java # Timer component
├── screen/        # UI screens and panels
│   ├── LayeredFrame.java # Multi-panel frame manager
│   ├── GamePanel.java    # Main game panel
│   ├── home/             # Home screen components
│   └── halt/             # End game screens
├── tile/          # Tile system
│   ├── Tile.java         # Base tile class
│   ├── EntityTile.java   # Entity tile wrapper
│   ├── plot/             # Terrain tiles
│   └── item/             # Interactive items
├── template/      # UI layout templates
└── tool/          # Utility classes
    ├── Algo.java         # A* pathfinding algorithm
    └── Util.java         # Helper functions
```

### Object Relationships

See [diagram.mmd](diagram.mmd) for detailed class relationships.

**Key Design Patterns:**
- **Abstract Factory** - Tile creation system
- **Observer** - Event handling for user input
- **State** - Entity and tile state management
- **Strategy** - Different movement behaviors for Player vs Mob
- **Singleton** - Map instance sharing across entities

---

## 🎮 How to Play

### Controls
- **Arrow Keys** - Move player (Up, Down, Left, Right)
- **Ctrl + 9** - Launch game (after window resize)
- **R** - Reset level
- **ESC or closing window** - Exit game

### Objective
Push all crates (📦) onto target locations (🎯) to complete the level.

### Rules
1. **Push, don't pull** - You can only push crates, not pull them
2. **One at a time** - Cannot push multiple crates simultaneously
3. **Avoid holes** - Stepping on holes ends the game
4. **Collect keys** - Unlock doors by collecting keys first
5. **Watch risky floors** - They disappear after one use

### Tips
- Plan your moves carefully - some positions are irreversible
- Use risky floors strategically
- Collect all keys before attempting door areas
- Watch out for mobs - they track your position

---

## 🚀 Running the Game

### Prerequisites
- Java 8 or higher
- IntelliJ IDEA (recommended) or any Java IDE

### Compilation & Execution

```bash
# Clone the repository
git clone https://github.com/webertom6/GameGridApp.git
cd GameGridApp

# Compile (from project root)
javac -d out src/be/weber/sokoban/code/game/RunGame.java

# Run
java -cp out be.weber.sokoban.code.game.RunGame
```

### Using IntelliJ IDEA
1. Open project in IntelliJ IDEA
2. Locate `RunGame.java` in `src/be/weber/sokoban/code/game/`
3. Right-click and select **Run 'RunGame.main()'**

---

## 🎓 Technical Highlights

### Multi-Threading Architecture
- **Player thread** - Handles user input and movement
- **Mob threads** - Independent AI entities with pathfinding
- **Audio thread** - Background music management
- **Event queue** - `LinkedBlockingQueue` for thread-safe input handling

### A* Pathfinding Implementation
```java
// Mob finds optimal path to player
BFSItem bfs = Algo.APath(this.map, 
                         mob_tile.getCoord().getY(), 
                         mob_tile.getCoord().getX());
```

### Map Cloning System
Deep cloning for game reset:
```java
// Create backup on init
this.map_reset = (Map) map.clone();

// Restore on reset
this.map = (Map) map_reset.clone();
game.relaunch(map);
```

### Dynamic UI Scaling
The game calculates aspect ratio and rescales all tiles:
```java
double r_w = (double) grid.getWidth() / grid.getWPixels();
double r_h = (double) grid.getHeight() / grid.getHPixels();
grid.setRatioW(r_w);
grid.setRatioH(r_h);
```

---

## 🔧 Recent Improvements

### ✅ Completed Features
- ✅ **Game reset functionality** - Complete level restart with proper state synchronization
- ✅ **Mob AI system** - A* pathfinding with real-time player tracking
- ✅ **Multi-entity support** - Multiple mobs with independent behavior
- ✅ **Resizable window** - Dynamic grid scaling
- ✅ **Clone system** - Deep copy for map and tile states
- ✅ **Sound control** - Mute/unmute and volume adjustment
- ✅ **Statistics tracking** - Steps, time, collectibles
- ✅ **Entity synchronization fix** - Mobs now correctly share map instance with player after reset

### 🐛 Known Limitations
As this was a first major project, some architectural decisions could be improved:
- ⚠️ Package hierarchy could be more modular
- ⚠️ Error handling could use custom exception types
- ⚠️ Some UI components could be refactored for better reusability

---

## 📊 Levels

### Level 1 - Basic Tutorial
Easy level from original specifications to learn mechanics.

### Level 2 - Advanced Puzzle
Introduces special tiles:
- Hidden keys under shift crates
- Multiple door sequences
- Risky floor navigation

**Solution hint**: Start with the upper section to find the second key.

### Zone Test
Developer testing area with experimental mechanics (accessible via level selector).

---

## 📝 Future Enhancements (I'm done but cool)

### Planned Features
- 🎯 Level editor for custom level creation
- 💾 Persistent scoreboard system
- 🌐 Multiple level progression system
- 🎨 Custom tile themes
- 🔊 Extended sound effects library
- 🎮 Gamepad controller support

### Possible Additions
- 🌀 **Portal tiles** - Teleportation mechanics
- 🤝 **Multiplayer mode** - Cooperative puzzle solving
- 📈 **Difficulty settings** - Adjustable mob speed and AI aggressiveness
- 🏆 **Achievement system** - Unlock rewards for special accomplishments

---

## 🤝 Contributing (dont care)

Contributions are welcome! Areas for improvement:
- Additional level designs
- Enhanced AI pathfinding algorithms
- UI/UX improvements
- Performance optimizations
- Bug fixes


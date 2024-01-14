import { system, world, Dimension, Player, Vector3 } from '@minecraft/server'
import { variables, secrets } from '@minecraft/server-admin'
import { HttpRequest, HttpRequestMethod } from '@minecraft/server-net'

interface PlayerLocation {
	name: string
	id: string
	dimension: Dimension
	location: Vector3
}

interface PlayerLocationMessage {
	world: string
	players: PlayerLocation[]
}

const delay = variables.get("tickDelay") as number
const authToken = secrets.get("authToken")
const worldName = variables.get("worldName") as string
const sendUrl = variables.get("sendUrl") as string

system.runInterval(updateAll, delay)

function updateAll() {
	const players = world.getAllPlayers()
	const message = {
		world: worldName,
		players: players.map((p: Player) => ({
			name: p.name,
			id: p.id,
			dimension: p.dimension,
			location: p.location
		}))
	}
	send(message)
}

function send(message: PlayerLocationMessage) {
	const req = new HttpRequest(sendUrl)
	req.addHeader("Content-Type", "application/json")
	req.addHeader("Authorization", authToken)
	req.setBody(JSON.stringify(message))
	req.setMethod(HttpRequestMethod.POST)
}
provider "azurerm" {
  features {}
  subscription_id = var.subscription_id
}

variable "subscription_id" {
  description = "Azure Subscription ID"
  default = "d593673b-0e4f-4d3b-96da-f863d69d9470"
}

variable "docker_image" {
  description = "Docker image name with tag"
}

resource "azurerm_resource_group" "rg" {
  name     = "resource-group"
  location = "West Europe"
}

resource "azurerm_virtual_network" "vn" {
  name                = "my-vnet"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  address_space       = ["10.0.0.0/16"]
}

resource "azurerm_subnet" "sub" {
  name                 = "my-subnet"
  resource_group_name  = azurerm_resource_group.rg.name
  virtual_network_name = azurerm_virtual_network.vn.name
  address_prefixes     = ["10.0.1.0/24"]
}

resource "azurerm_container_group" "container" {
  name                = "my-container-group"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"

  container {
    name   = "task-service"
    image  = var.docker_image
    cpu    = "1.0"
    memory = "1.5"

    ports {
      port     = 80
      protocol = "TCP"
    }
  }

  tags = {
    environment = "dev"
  }
}